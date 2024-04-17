package com.softanime.recipeapp.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.softanime.recipeapp.R
import com.softanime.recipeapp.data.models.recipe.ResponseRecipe.Result
import com.softanime.recipeapp.databinding.ItemRecipeBinding
import com.softanime.recipeapp.utils.BaseDiffUtils
import com.softanime.recipeapp.utils.minToHour
import com.softanime.recipeapp.utils.setDynamicallyColor
import javax.inject.Inject

class RecipeAdapter @Inject constructor() : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    // Binding
    private lateinit var binding: ItemRecipeBinding

    // Data
    private var items = emptyList<Result>()

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.initAnim()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clearAnim()
    }

    // VH
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Result) {
            binding.apply {
                // Text
                txtName.text = item.title
                val htmlFormatter = HtmlCompat.fromHtml(item.summary,HtmlCompat.FROM_HTML_MODE_COMPACT)
                txtDesc.text = htmlFormatter
                txtLike.text = item.aggregateLikes.toString()
                txtTime.text = item.cookingMinutes.minToHour()
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

        fun initAnim(){
            binding.root.animation = AnimationUtils.loadAnimation(context,R.anim.recipe_item_anim)
        }
        fun clearAnim(){
            binding.root.clearAnimation()
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