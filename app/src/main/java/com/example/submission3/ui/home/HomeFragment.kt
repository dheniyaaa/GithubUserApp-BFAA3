package com.example.submission3.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.submission3.databinding.HomeFragmentBinding
import com.example.submission3.utils.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {


    private lateinit var homeViewModel: HomeViewModel
    override val kodein: Kodein by kodein()
    private val viewModelFactory: ViewModelFactory by instance()
    private var _binding : HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        isDarkMode()
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isDarkMode(){
        homeViewModel.getThemeSetting().observe(viewLifecycleOwner){isDarkModeActive ->
         if (isDarkModeActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
         else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}