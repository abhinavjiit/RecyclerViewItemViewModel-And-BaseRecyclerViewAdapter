package com.example.pristencare.adapter

import com.example.pristencare.utils.FeedItem
import com.example.pristencare.utils.IBaseRecyclerViewAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ImageRecyclerViewAdapter : IBaseRecyclerViewAdapter<FeedItem>(mutableListOf()) {
    override fun registerItemClick(holder: BaseBindingViewHolder, viewType: Int, position: Int) {
    }
}