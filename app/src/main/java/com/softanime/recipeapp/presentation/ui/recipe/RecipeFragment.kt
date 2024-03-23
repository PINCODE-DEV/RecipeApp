package com.softanime.recipeapp.presentation.ui.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.softanime.recipeapp.R
import com.softanime.recipeapp.databinding.FragmentRecipeBinding
import com.softanime.recipeapp.databinding.FragmentRegisterBinding
import com.softanime.recipeapp.presentation.viewModels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    // Binding
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate Views
        _binding = FragmentRecipeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Init Views
        lifecycleScope.launch {
            setupViews()
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun setupViews() {
        viewModel.readUserInfo.collect {
            binding.txtUsername.text =
                "${getString(R.string.hello)}, mr-${it.username} ${getEmojiByUnicode()}"
        }
    }

    private fun getEmojiByUnicode(): String {
        return String(Character.toChars(0x1f44b))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}