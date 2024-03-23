package com.softanime.recipeapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.softanime.recipeapp.R
import com.softanime.recipeapp.data.models.recipe.ResponseRecipe.Result
import com.softanime.recipeapp.databinding.ItemPopularBinding
import com.softanime.recipeapp.utils.BaseDiffUtils
import com.softanime.recipeapp.utils.Constants
import javax.inject.Inject

class PopularAdapter @Inject constructor() : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    // Binding
    private lateinit var binding: ItemPopularBinding

    // Data
    private var items = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    // VH
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Result) {
            binding.apply {
                // Text
                txtPopularName.text = item.title
                txtPopularPrice.text = "${item.pricePerServing} $"

                // Image
                val imageUrl = item.image
                val imageSplit = imageUrl.split("-")
                val imageSize =
                    imageSplit[1].replace(Constants.OLD_IMAGE_SIZE, Constants.NEW_IMAGE_SIZE)
                imgPopular.load("${imageSplit[0]}-$imageSize") {
                    crossfade(true)
                    crossfade(800)
                    error(R.drawable.ic_placeholder)
                    memoryCachePolicy(CachePolicy.ENABLED)
                }
                // Click
                root.setOnClickListener {
                    onItemCLickListener?.let {
                        it(item.id)
                    }
                }
            }
        }
    }

    private var onItemCLickListener: ((Int) -> Unit)? = null
    fun setOnItemCLickListener(listener: (Int) -> Unit) {
        onItemCLickListener = listener
    }

    fun setData(data: List<Result>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}