package com.example.newscatcherapp.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newscatcherapp.R
import com.example.newscatcherapp.adapter.NewsAdapter
import com.example.newscatcherapp.api.RetrofitInstance
import com.example.newscatcherapp.databinding.ActivityMainBinding
import com.example.newscatcherapp.fragments.NewsFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(view)



        val navView : BottomNavigationView by lazy {
            binding.bottomNavigationView
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{ _, nd: NavDestination, _->
            if(nd.id == R.id.newsFragment || nd.id == R.id.saveWindowFragment){
                navView.visibility = View.VISIBLE
            }else{
                navView.visibility = View.GONE
            }
        }
    }
}