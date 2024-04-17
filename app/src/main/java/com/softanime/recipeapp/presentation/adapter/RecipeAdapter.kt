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
import com.softanime.recipeapp.databinding.ItemRecipeBinding
import com.softanime.recipeapp.utils.BaseDiffUtils
import com.softanime.recipeapp.utils.setDynamicallyColor
import javax.inject.Inject

class RecipeAdapter @Inject constructor() : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    // Binding
    private lateinit var binding: ItemRecipeBinding

    // Data
    private var items = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    // VH
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Result) {
            binding.apply {
                // Text
                txtName.text = item.title
                txtDesc.text = item.summary
                txtLike.text = item.aggregateLikes.toString()
                txtTime.text = item.cookingMinutes.toString()
                txtHealth.text = item.healthScore.toString()

                // Image
                imgRecipe.load(item.image){
                    crossfade(true)
                    crossfade(800)
                    error(R.drawable.ic_placeholder)
                    memoryCachePolicy(CachePolicy.ENABLED)
                }

                // --- Dynamically State ---
                // Check Vegan
                if (item.vegan)
                    txtVegan.setDynamicallyColor(R.color.caribbean_green)
                else
                    txtVegan.setDynamicallyColor(R.color.gray)

                // Healthy
                when (item.healthScore) {
                    in 90..100 -> txtHealth.setDynamicallyColor(R.color.caribbean_green)
                    in 60..89 -> txtHealth.setDynamicallyColor(R.color.chineseYellow)
                    in 0..59 -> txtHealth.setDynamicallyColor(R.color.tart_orange)
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