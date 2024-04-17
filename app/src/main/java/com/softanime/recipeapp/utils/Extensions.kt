package com.softanime.recipeapp.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun RecyclerView.setupRecyclerView(
    lM: RecyclerView.LayoutManager,
    myAdapter: RecyclerView.Adapter<*>
) {
    this.apply {
        layoutManager = lM
        adapter = myAdapter
        hasFixedSize()
    }
}

fun TextView.setDynamicallyColor(color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
    this.compoundDrawables[1].setTint(ContextCompat.getColor(context, color))
}

fun Int.minToHour(): String {
    val hour = this / 60
    val min = this % 60

    return if (hour > 0) "${hour}h:${min}min" else "${min}min"
}