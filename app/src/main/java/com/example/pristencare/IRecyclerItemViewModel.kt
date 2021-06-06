package com.example.pristencare

import androidx.annotation.LayoutRes

interface IRecyclerItemViewModel {

    @LayoutRes
    fun getLayoutId(): Int

    fun getBindingPairs(): List<Pair<Int, Any>>

}