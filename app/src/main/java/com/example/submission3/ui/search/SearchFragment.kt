@file:Suppress("DEPRECATION")

package com.example.submission3.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.R
import com.example.submission3.databinding.SearchFragmentBinding
import com.example.submission3.ui.detail.DetailActivity
import com.example.submission3.utils.ViewModelFactory
import com.example.submission3.vstate.State
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SearchFragment : Fragment(), KodeinAware {

    private lateinit var listUserAdapter: ListUserAdapter
    override val kodein: Kodein by kodein()
    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var searchViewModel: SearchViewModel
    private var _binding : SearchFragmentBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        searchViewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        isDarkMode()
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listUserAdapter = ListUserAdapter {
            val userDetail = Intent(context, DetailActivity::class.java).putExtra(DetailActivity.DATA_USER, it)
            startActivity(userDetail)
        }

        setRecyclerview()

        searchUser()

        val checkDarkMode = this.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
        val bgSearch = this.resources.getDrawable(R.drawable.bg_tab_search_dark)
        if (checkDarkMode == Configuration.UI_MODE_NIGHT_YES){
            binding.searchBar.background = bgSearch
        }

    }

    private fun searchUser(){
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()){
                    return true
                } else {
                    isLoading(true)
                    isUserNotFound(false)
                    getDataUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataUser(username: String) {
        searchViewModel.getUser(username).observe(viewLifecycleOwner){
            it.let { resource ->
                when(resource.status){
                    State.SUCCESS -> {
                        isLoading(false)

                        if (it.data != null){
                            isUserNotFound(false)
                            listUserAdapter.setDataUser(it.data)
                            listUserAdapter.notifyDataSetChanged()
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

    private fun isUserNotFound(state: Boolean) {
        when(state){
            true -> {
                binding.imgMessageSearch.visibility = View.VISIBLE
                binding.tvNotFound.text = resources.getString(R.string.user_not_found)
                binding.tvNotFound.visibility = View.VISIBLE
                binding.rvUserSearch.visibility = View.GONE
            }
            false -> {
                binding.imgMessageSearch.visibility = View.GONE
                binding.tvNotFound.visibility = View.GONE
            }
        }
    }

    private fun isLoading(state: Boolean) {
        when(state){
            true -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvUserSearch.visibility = View.GONE
            }
            false -> {
                binding.progressBar.visibility = View.GONE
                binding.rvUserSearch.visibility = View.VISIBLE
            }
        }
    }

    private fun isDarkMode(){
        searchViewModel.getThemeSetting().observe(viewLifecycleOwner){ isDarkModeActive ->
            if (isDarkModeActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setRecyclerview(){
        with(binding.rvUserSearch){
            layoutManager = LinearLayoutManager(context)
            adapter = listUserAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}