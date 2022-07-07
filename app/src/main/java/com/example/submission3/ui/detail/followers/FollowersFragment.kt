package com.example.submission3.ui.detail.followers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.databinding.FollowersFragmentBinding
import com.example.submission3.ui.detail.DetailActivity
import com.example.submission3.ui.detail.FollowAdapter
import com.example.submission3.utils.Preference
import com.example.submission3.utils.ViewModelFactory
import com.example.submission3.vstate.State
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FollowersFragment : Fragment(), KodeinAware {

    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followAdapter: FollowAdapter
    private val viewModelFactory: ViewModelFactory by instance()
    override val kodein: Kodein by kodein()
    private  var _binding: FollowersFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FollowersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followersViewModel = ViewModelProvider(this, viewModelFactory)[FollowersViewModel::class.java]

        followAdapter =  FollowAdapter{
            val userDetail = Intent(context, DetailActivity::class.java).putExtra(DetailActivity.DATA_USER, it)
            startActivity(userDetail)
        }

        val usernamePref = Preference(requireContext())
        val usernameUser = usernamePref.getValues(DetailActivity.USERNAME_USER)

        setRecyclerview()

        isLoading(true)

        getDataUser(usernameUser.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataUser(user: String){
        followersViewModel.getFollowers(user).observe(viewLifecycleOwner){
            it.let { resource ->
                when(resource.status){
                    State.SUCCESS -> {
                        isLoading(false)

                        if (it.data != null){
                            isUserNotFound(false)
                            followAdapter.setDataUser(it.data)
                            followAdapter.notifyDataSetChanged()
                            if (it.data.isEmpty()){
                                isUserNotFound(true)
                            }
                        }
                    }
                    State.LOADING -> {
                        isLoading(true)
                        isUserNotFound(false)
                    }
                    State.ERROR -> {
                        isLoading(false)
                        isUserNotFound(true)
                    }
                }
            }
        }
    }

    private fun setRecyclerview(){
        with(binding.rvUserFollowers){
            layoutManager = LinearLayoutManager(context)
            adapter = followAdapter
        }
    }

    private fun isLoading(state: Boolean){
        when(state){
            true -> {
                binding.progressBarFollowers.visibility = View.VISIBLE
                binding.rvUserFollowers.visibility = View.GONE
                isUserNotFound(false)
            }
            false -> {
                binding.progressBarFollowers .visibility = View.GONE
                binding.rvUserFollowers.visibility = View.VISIBLE
            }
        }

    }

    private fun isUserNotFound(state: Boolean) {
        when(state){
            true -> {
                binding.tvFollowers.visibility = View.VISIBLE
                binding.rvUserFollowers.visibility = View.GONE
            }
            false -> {
                binding.tvFollowers.visibility = View.GONE
            }
        }

    }


}