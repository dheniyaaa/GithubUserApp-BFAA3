package com.example.submission3.ui.detail

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission3.R
import com.example.submission3.data.source.local.entity.UserModel
import com.example.submission3.databinding.ActivityDetailBinding
import com.example.submission3.utils.Preference
import com.example.submission3.utils.ViewModelFactory
import com.example.submission3.utils.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DetailActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userData : UserModel
    private lateinit var detailViewModel: DetailViewModel
    override val kodein: Kodein by kodein()
    private val viewModelFactory: ViewModelFactory by instance()

    companion object{
        const val DATA_USER = "DATA_USER"
        const val USERNAME_USER = "USERNAME_USER"

        private val TAB_TITLE = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = obtainViewModel(this@DetailActivity)

        userData = intent.getParcelableExtra<UserModel>(DATA_USER) as UserModel
        setDetailUser(userData)
        setDatafavorite(
            userData.username,
            userData.name?:"-",
            userData.repository ?:0,
            userData.followers ?:0,
            userData.following ?:0,
            userData.company?:"-",
            userData.location?:"-",
            userData.image?:"-"
        )

        setTabLayout()

        val usernamepref = Preference(this)
        usernamepref.setValues(USERNAME_USER, userData.username)

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {

        return ViewModelProvider(activity, viewModelFactory)[DetailViewModel::class.java]

    }

    private fun setTabLayout(){
        val viewPager = binding.viewPager

        val sectionPagerAdapter = SectionPagerAdapter(this)
        viewPager.adapter = sectionPagerAdapter
        viewPager.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator(binding.tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()

        val tabs = binding.tabs.getChildAt(0) as ViewGroup
        val tab = tabs.getChildAt(0)
        val layoutParams = tab.layoutParams as LinearLayout.LayoutParams

        layoutParams.marginEnd = 100
        tab.layoutParams = layoutParams

        binding.tabs.requestLayout()
    }


    private fun setDetailUser(dataUser: UserModel){
        with(binding){
            usernameDetailUser.text = dataUser.username
            nameDetailUser.text = dataUser.name
            Glide.with(this@DetailActivity)
                .load(dataUser.image)
                .into(imgDetailUser)
            company.text = dataUser.company
            location.text = dataUser.location
            valueRepositories.text = dataUser.repository.toString()
            valueFollowers.text = dataUser.followers.toString()
            valueFollowing.text =dataUser.following.toString()


        }
    }

    private fun setButtonFavorite(favorite: Boolean){
        val txtAddFavorite = resources.getString(R.string.add_favorite)
        val txtRemoveFavorite = resources.getString(R.string.remove_favorite)

        val colorwhite = ContextCompat.getColor(this, R.color.white)
        val colorgrey = ContextCompat.getColor(this, R.color.grey_fav)

        val btnfav = ContextCompat.getDrawable(this, R.drawable.bg_background_addfavorite)
        val btnUnFav = ContextCompat.getDrawable(this, R.drawable.bg_background_tab_unfavorite)


        if (favorite){
            binding.btnFavorite.text = txtRemoveFavorite
            binding.btnFavorite.setTextColor(colorgrey)
            binding.btnFavorite.background = btnUnFav
        }
        else{
            binding.btnFavorite.text = txtAddFavorite
            binding.btnFavorite.setTextColor(colorwhite)
            binding.btnFavorite.background = btnfav
        }

    }

    private fun setDatafavorite(username: String,
                                name: String,
                                repository: Int,
                                followers: Int,
                                following: Int,
                                company: String,
                                location: String,
                                image: String){
        var isFavorite = false

        CoroutineScope(Dispatchers.IO).launch {
            val checkUser = detailViewModel.getUserByUsername(username)

            withContext(Dispatchers.Main){
                when{
                    checkUser != null -> isFavorite = true
                    else -> isFavorite = false
                }

                setButtonFavorite(isFavorite)
            }
        }

        binding.btnFavorite.setOnClickListener{
            isFavorite = !isFavorite

            CoroutineScope(Dispatchers.IO).launch {
                if (isFavorite){
                    detailViewModel.addToFavorite(username, name, repository, followers, following, company, location, image)
                }
                else{
                    detailViewModel.deleteFavorite(username)
                }
            }
            setButtonFavorite(isFavorite)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }






















}
