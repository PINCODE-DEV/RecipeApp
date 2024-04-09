package com.softanime.recipeapp.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String){
    Snackbar.make(this,message,Snackbar.LENGTH_SHORT).show()
}

fun RecyclerView.setupRecyclerView(lM : RecyclerView.LayoutManager, myAdapter: RecyclerView.Adapter<*>){
    this.apply {
        layoutManager = lM
        adapter= myAdapter
        hasFixedSize()
    }
}