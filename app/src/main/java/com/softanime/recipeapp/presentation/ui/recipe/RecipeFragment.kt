package com.softanime.recipeapp.presentation.ui.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.softanime.recipeapp.R
import com.softanime.recipeapp.databinding.FragmentRecipeBinding
import com.softanime.recipeapp.presentation.adapter.PopularAdapter
import com.softanime.recipeapp.presentation.adapter.RecipeAdapter
import com.softanime.recipeapp.presentation.viewModels.RecipeViewModel
import com.softanime.recipeapp.presentation.viewModels.RegisterViewModel
import com.softanime.recipeapp.utils.Constants
import com.softanime.recipeapp.utils.NetworkRequest
import com.softanime.recipeapp.utils.setupRecyclerView
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    // Binding
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val registerViewModel: RegisterViewModel by viewModels()
    private val viewModel: RecipeViewModel by viewModels()

    // Adapters
    @Inject
    lateinit var popularAdapter: PopularAdapter
    @Inject
    lateinit var recipeAdapter: RecipeAdapter

    // Slider Position
    private var bannerIndex = 0

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
        // Load Popular Data
        loadPopularData()
        // Load Recipe Data
        loadRecipeData()
    }

    @SuppressLint("SetTextI18n")
    private suspend fun setupViews() {
        registerViewModel.readUserInfo.collect {
            binding.txtUsername.text =
                "${getString(R.string.hello)}, mr-${it.username} ${getEmojiByUnicode()}"
        }
    }

    private fun loadPopularData() {
        // Call Popular Api
        viewModel.callPopularApi(viewModel.popularQueries())

        // Collect Popular Data
        viewModel.popularData.observe(viewLifecycleOwner) { response ->
            binding.apply {
                when (response) {
                    is NetworkRequest.LOADING -> {
                        setupLoading(true, popularList)
                    }

                    is NetworkRequest.SUCCESS -> {
                        setupLoading(false, popularList)
                        response.data?.let { data ->
                            if (response.data.results.isNotEmpty()) {
                                popularAdapter.setData(data.results)
                                initPopularRecycler(data.results.size)
                            }
                        }
                    }

                    is NetworkRequest.ERROR -> {
                        setupLoading(false, popularList)
                    }
                }
            }
        }
    }

    private fun loadRecipeData() {
        // Call Recipe Api
        viewModel.callRecipeApi(viewModel.recipeQueries())

        // Collect Popular Data
        viewModel.recipeData.observe(viewLifecycleOwner) { response ->
            binding.apply {
                when (response) {
                    is NetworkRequest.LOADING -> {
                        setupLoading(true, recipesList)
                    }

                    is NetworkRequest.SUCCESS -> {
                        setupLoading(false, recipesList)
                        response.data?.let { data ->
                            if (response.data.results.isNotEmpty()) {
                                recipeAdapter.setData(data.results)
                                initRecipeRecycler()
                            }
                        }
                    }

                    is NetworkRequest.ERROR -> {
                        setupLoading(false, recipesList)
                    }
                }
            }
        }
    }

    private fun initPopularRecycler(listSize: Int) {
        binding.popularList.setupRecyclerView(
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
            popularAdapter
        )
        // Item Click
        popularAdapter.setOnItemCLickListener {

        }
        // Snap
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.popularList)

        // Auto Scroll
        lifecycleScope.launch {
            repeat(Int.MAX_VALUE) {
                delay(Constants.AUTO_SCROLL_TIME)
                if (bannerIndex < listSize)
                    bannerIndex += 1
                else
                    bannerIndex = 0

                binding.popularList.smoothScrollToPosition(bannerIndex)
            }
        }
    }

    private fun initRecipeRecycler() {
        binding.recipesList.setupRecyclerView(
            LinearLayoutManager(requireContext()),
            recipeAdapter
        )
        // Item Click
        recipeAdapter.setOnItemCLickListener {

        }
    }

    private fun setupLoading(shown: Boolean, shimmer: ShimmerRecyclerView) {
        shimmer.apply {
            if (shown)
                showShimmer()
            else
                hideShimmer()
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