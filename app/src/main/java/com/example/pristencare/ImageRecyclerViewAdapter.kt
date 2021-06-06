package com.example.pristencare

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ImageRecyclerViewAdapter : IBaseRecyclerViewAdapter<FeedItem>(mutableListOf()) {
    override fun registerItemClick(holder: BaseBindingViewHolder, viewType: Int, position: Int) {
    }
}