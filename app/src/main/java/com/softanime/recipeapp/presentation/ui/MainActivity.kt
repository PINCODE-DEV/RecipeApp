package com.softanime.recipeapp.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.softanime.recipeapp.R
import com.softanime.recipeapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.inflationx.viewpump.ViewPumpContextWrapper


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Binding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // Nav Host
    private lateinit var navHost: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Init Views()
        setupViews()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        return super.navigateUpTo(upIntent) || super.onNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupViews() {
        binding.apply {
            // Init NavHost
            navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
            // Other
            mainBottomNav.background = null
            mainBottomNav.setupWithNavController(navHost.navController)

            // Check current destination
            navHost.navController.addOnDestinationChangedListener{_,destination,_ ->
                when(destination.id){
                    R.id.splashFragment -> showBottomNav(false)
                    R.id.registerFragment -> showBottomNav(false)
                    else -> showBottomNav(true)
                }
            }
        }
    }

    private fun showBottomNav(state: Boolean) {
        if (state) {
            binding.mainBottomAppbar.isVisible = true
            binding.mainFabMenu.isVisible = true
        } else {
            binding.mainBottomAppbar.isVisible = false
            binding.mainFabMenu.isVisible = false
        }
    }
}