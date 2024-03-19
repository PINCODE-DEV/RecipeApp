package com.softanime.recipeapp.presentation.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.softanime.recipeapp.R
import com.softanime.recipeapp.databinding.FragmentRegisterBinding
import com.softanime.recipeapp.databinding.FragmentSplashBinding
import com.softanime.recipeapp.presentation.viewModels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment : Fragment() {
    // Binding
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate Views
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Init Views
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            // Set back Img
            imgBg.load(R.drawable.bg_splash)

            lifecycleScope.launchWhenCreated {
                delay(2600)
                viewModel.readUserInfo.asLiveData().observe(viewLifecycleOwner){
                    findNavController().popBackStack(R.id.splashFragment,true)
                    // Check SignIn
                    if (it.username.isNotEmpty()){
                        //findNavController().navigate(R.id.actionToRecipe)
                        Toast.makeText(requireContext(),it.username,Toast.LENGTH_LONG).show()
                    }
                    else
                        findNavController().navigate(R.id.actionToRegister)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}