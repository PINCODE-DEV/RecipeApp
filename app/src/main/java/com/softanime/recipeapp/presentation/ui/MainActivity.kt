package com.softanime.recipeapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.softanime.recipeapp.R
import com.softanime.recipeapp.databinding.ActivityMainBinding

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
        }
    }
}