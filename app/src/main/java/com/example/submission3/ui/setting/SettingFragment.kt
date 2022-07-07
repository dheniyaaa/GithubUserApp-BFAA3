package com.example.submission3.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.submission3.R
import com.example.submission3.databinding.SettingFragmentBinding
import com.example.submission3.utils.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SettingFragment : Fragment(), KodeinAware {

    private lateinit var settingViewModel: SettingViewModel
    private val viewModelFactory: ViewModelFactory by instance()
    override val kodein: Kodein by kodein()
    private var _binding : SettingFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingViewModel = ViewModelProvider(this, viewModelFactory)[SettingViewModel::class.java]
        darkMode()
    }

    private fun darkMode(){
        var isDarkMode = false

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner){isDarkModeActive ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.imgTheme.setImageResource(R.drawable.ic_dark_mode)
                binding.tvTheme.text = resources.getText(R.string.night_mode)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.imgTheme.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
                binding.tvTheme.text = resources.getText(R.string.light_mode)
            }
        }

        binding.imgTheme.setOnClickListener{
            isDarkMode = !isDarkMode
            settingViewModel.saveThemeSetting(isDarkMode)
        }


    }

   override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}