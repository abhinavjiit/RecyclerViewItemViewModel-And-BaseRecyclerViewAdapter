package com.example.pristencare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pristencare.BR
import com.example.pristencare.ItemViewModel
import com.example.pristencare.R
import com.example.pristencare.model.Photo

class RecyclerViewImageAdapter(val lifecycle: LifecycleOwner) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val itemList = arrayListOf<Photo>()
    private val itemViewModel = arrayListOf<ItemViewModel>()
    private var recyclerView: RecyclerView? = null


    init {
        lifecycle.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                recyclerView?.let { parent ->
                    val childCount = parent.childCount
                    for (i in 0 until childCount) {
                        parent.getChildAt(i)?.let {
                            (parent.getChildViewHolder(it) as MyViewHolder)
                                .run {
                                    onDestroy()
                                }
                        }
                    }
                }

            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                recyclerView?.let { parent ->
                    val childCount = parent.childCount
                    for (i in 0 until childCount) {
                        parent.getChildAt(i)?.let {
                            (parent.getChildViewHolder(it) as MyViewHolder)
                                .run {
                                    onStop()
                                }
                        }
                    }
                }
            }


            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                recyclerView?.run {
                    if (layoutManager is LinearLayoutManager) {
                        val first =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        val last =
                            (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (first in 0..last)
                            for (i in first..last) {
                                findViewHolderForAdapterPosition(i)?.let {
                                    (it as MyViewHolder).onStart()
                                }
                            }
                    }
                }
            }
        })

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.image_item,
                parent,
                false
            ) as ViewDataBinding
        return MyViewHolder(binding, lifecycle)

    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    fun setListData(itemList: List<Photo>, itemViewModel: List<ItemViewModel>) {
        this.itemList.addAll(itemList)
        this.itemViewModel.addAll(itemViewModel)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bind(position)
    }


    inner class MyViewHolder(val binding: ViewDataBinding, val parentLife: LifecycleOwner) :
        RecyclerView.ViewHolder(binding.root),
        LifecycleOwner {


        private val lifecycleRegistry = LifecycleRegistry(this)

        init {
            binding.lifecycleOwner = this
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
            lifecycleRegistry.currentState = Lifecycle.State.CREATED      /// ON_CREATE EVENTS
        }

        fun bind(position: Int) {
            itemViewModel[position].lifecycleRegistry = lifecycleRegistry
            itemViewModel[position].position = position
            lifecycleRegistry.addObserver(itemViewModel[position])
            binding.setVariable(BR.item, itemList[position])
            binding.setVariable(BR.itemViewModel, itemViewModel[position])
            binding.executePendingBindings()
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }


        fun onStart() {
            //   binding.invalidateAll()
            lifecycleRegistry.currentState = Lifecycle.State.STARTED     //
            lifecycleRegistry.currentState = Lifecycle.State.RESUMED     //   ON_RESUME EVENT
        }

        fun onStop() {
            lifecycleRegistry.currentState = Lifecycle.State.STARTED    //
            lifecycleRegistry.currentState = Lifecycle.State.CREATED    //     ON_STOP EVENT

        }

        fun onDestroy() {
            lifecycleRegistry.currentState = Lifecycle.State.DESTROYED   ///  ON_DESTROY EVENT
            // binding.unbind()
            itemViewModel[absoluteAdapterPosition].doCancel(absoluteAdapterPosition)
        }

    }


    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        (holder as MyViewHolder).onStart()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        (holder as MyViewHolder).apply {
            //  binding.unbind()
            onStop()
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

}