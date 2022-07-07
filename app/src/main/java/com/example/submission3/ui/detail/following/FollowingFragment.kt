package com.example.submission3.ui.detail.following

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.databinding.FollowingFragmentBinding
import com.example.submission3.ui.detail.DetailActivity
import com.example.submission3.ui.detail.FollowAdapter
import com.example.submission3.utils.Preference
import com.example.submission3.utils.ViewModelFactory
import com.example.submission3.vstate.State
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FollowingFragment : Fragment(), KodeinAware {

    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var followAdapter: FollowAdapter
    private val viewModelFactory: ViewModelFactory by instance()
    override val kodein: Kodein by kodein()
    private  var _binding: FollowingFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FollowingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingViewModel = ViewModelProvider(this, viewModelFactory)[FollowingViewModel::class.java]

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
        followingViewModel.getFollowingUser(user).observe(viewLifecycleOwner){
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
        with(binding.rvUserFollowing){
            layoutManager = LinearLayoutManager(context)
            adapter = followAdapter
        }
    }

    private fun isLoading(state: Boolean){
        when(state){
            true -> {
                binding.progressBarFollowing.visibility = View.VISIBLE
                binding.rvUserFollowing.visibility = View.GONE
                isUserNotFound(false)
            }
            false -> {
                binding.progressBarFollowing.visibility = View.GONE
                binding.rvUserFollowing.visibility = View.VISIBLE
            }
        }

    }

    private fun isUserNotFound(state: Boolean) {
        when(state){
            true -> {
                binding.tvFollowing.visibility = View.VISIBLE
                binding.rvUserFollowing.visibility = View.GONE
            }
            false -> {
                binding.tvFollowing.visibility = View.GONE
            }
        }

    }


}