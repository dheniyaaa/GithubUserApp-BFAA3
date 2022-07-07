package com.example.submission3.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.databinding.FavoriteFragmentBinding
import com.example.submission3.ui.detail.DetailActivity
import com.example.submission3.ui.search.ListUserAdapter
import com.example.submission3.utils.Convert
import com.example.submission3.utils.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FavoriteFragment : Fragment(), KodeinAware {

    private lateinit var listUserAdapter: ListUserAdapter
    override val kodein: Kodein by kodein()
    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var favoriteViewModel: FavoriteViewModel
    private  var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        favoriteViewModel =
            ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listUserAdapter = ListUserAdapter {
            val userDetail = Intent(context, DetailActivity::class.java).putExtra(DetailActivity.DATA_USER, it)
            startActivity(userDetail)
        }

        setRecycleview()

        getDataFavorite()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getDataFavorite(){
        favoriteViewModel.getAllFavoriteUser()?.observe(viewLifecycleOwner){
            val userData = Convert().convertListToArrayList(it)
            CoroutineScope(Dispatchers.Main).launch {
                if (it != null){
                    isUserNotFound(false)
                    listUserAdapter.setDataUser(userData)
                    listUserAdapter.notifyDataSetChanged()

                    if (userData.isEmpty()){
                        isUserNotFound(true)
                    }
                }
            }
        }
    }

    private fun setRecycleview(){
        with(binding.rvUserFavorite){
            layoutManager = LinearLayoutManager(context)
            adapter = listUserAdapter
        }
    }

    private fun isUserNotFound(state: Boolean){
        when(state){
            true -> {
                binding.tvNotUserFavorite.visibility = View.VISIBLE
                binding.rvUserFavorite.visibility = View.GONE
            }
            false -> {
                binding.tvNotUserFavorite.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}