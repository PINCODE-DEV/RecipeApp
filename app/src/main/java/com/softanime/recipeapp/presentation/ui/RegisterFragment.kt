package com.softanime.recipeapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.softanime.recipeapp.R
import com.softanime.recipeapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    // Binding
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate Views
        _binding = FragmentRegisterBinding.inflate(layoutInflater,container,false)
        //Init Views
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        binding.apply {
            coverImg.load(R.drawable.register_logo)
        }
    }
}