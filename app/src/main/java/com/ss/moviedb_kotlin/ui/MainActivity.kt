package com.ss.moviedb_kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.ss.moviedb_kotlin.R
import com.ss.moviedb_kotlin.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
        setListener()
    }

    private fun setListener() {
        // * Expand app bar on fragment change
        // https://developer.android.com/guide/navigation/navigation-ui#kotlin
        navController.addOnDestinationChangedListener { _, _, _ ->
            binding.appbar.setExpanded(true, true)
        }
    }

    private fun init() {
        setSupportActionBar(binding.toolbar)

        //==Start of navigation based on documentation
        // * NavController
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // * Custom navigation - can set top-level destination and fallback when no back stack found
        appBarConfiguration =
            AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.home_fragment,
                    R.id.now_playing_fragment,
                    R.id.upcoming_fragment
                ),
                fallbackOnNavigateUpListener = ::onSupportNavigateUp
            )

        // * Action bar
        setupActionBarWithNavController(this, navController, appBarConfiguration)

        // * Bottom navigation
        setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.apply {
            // * Always set selected menu item as active
            setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, navController)

                return@setOnItemSelectedListener true
            }

            // * Pop all back stack above active menu item
            setOnItemReselectedListener { item ->
                navController.popBackStack(item.itemId, inclusive = false)
            }
        }
        //==End of navigation based on documentation
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}