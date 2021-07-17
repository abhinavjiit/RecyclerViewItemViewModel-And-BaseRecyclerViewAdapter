package com.example.pristencare.utils

import com.example.pristencare.BR
import com.example.pristencare.IRecyclerItemViewModel
import com.example.pristencare.R
import com.example.pristencare.model.Photo
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
sealed class FeedItem(private val layoutId: Int) :
    IRecyclerItemViewModel {

    override fun getLayoutId(): Int = layoutId

}

//@ExperimentalCoroutinesApi
//class ImageItem(private val photo: Photo) : FeedItem(
//    R.layout.image_item
//) {
//    override fun getBindingPairs(): List<Pair<Int, Any>> {
//        return
//    }
//
//}

@ExperimentalCoroutinesApi
class DefaultItem() : FeedItem(R.layout.default_item_shimmer_layout) {
    override fun getBindingPairs(): List<Pair<Int, Any>> {
        return emptyList()
    }

}
