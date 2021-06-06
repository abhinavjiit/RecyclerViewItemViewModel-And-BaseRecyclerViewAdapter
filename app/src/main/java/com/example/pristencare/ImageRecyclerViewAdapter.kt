package com.example.pristencare

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ImageRecyclerViewAdapter : IBaseRecyclerViewAdapter<FeedItem>(mutableListOf()) {
    override fun registerItemClick(holder: BaseBindingViewHolder, viewType: Int, position: Int) {
    }
}