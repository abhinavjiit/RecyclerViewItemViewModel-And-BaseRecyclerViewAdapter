package com.example.pristencare.utils

import com.example.pristencare.BR
import com.example.pristencare.IRecyclerItemViewModel
import com.example.pristencare.model.Photo
import com.example.pristencare.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
sealed class FeedItem(private val layoutId: Int) :
    IRecyclerItemViewModel {

    override fun getLayoutId(): Int = layoutId

}

@ExperimentalCoroutinesApi
class ImageItem(private val photo: Photo) : FeedItem(
    R.layout.image_item
) {
    override fun getBindingPairs(): List<Pair<Int, Any>> {
        return listOf(BR.item to photo)
    }

}
