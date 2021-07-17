package com.example.pristencare.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pristencare.*
import com.example.pristencare.adapter.RecyclerViewImageAdapter
import com.example.pristencare.apiservice.ApiService
import com.example.pristencare.databinding.ActivityMainBinding
import com.example.pristencare.domain.RepositoryImpl
import com.example.pristencare.model.Photo
import com.example.pristencare.model.RequestModel
import com.example.pristencare.utils.IResult
import com.example.pristencare.viewmodel.MainActivityViewMOdelFactory
import com.example.pristencare.viewmodel.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

const val LIST = 0
const val GRID_2 = 1
const val GRID_3 = 2

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), LifecycleObserver {

    private lateinit var binding: ActivityMainBinding


    private lateinit var endlessScrollListener: EndlessScrollListener

    private var manager = GridLayoutManager(this, 1)

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewMOdelFactory(
            RepositoryImpl(
                retrofit
            )
        )
    }
    private var requestModel = RequestModel()

    private val itemList = arrayListOf<Photo>()

    private val Imageadapter by lazy { RecyclerViewImageAdapter(this) }

    @Inject
    @MainRetrofit
    lateinit var retrofit: ApiService

    @Inject
    @ItemViewModelRetrofit
    lateinit var itemViewModelRetrofit: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        (BaseApplication.getInstance() as Injector).createAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        setupLayoutManager()
        doApiCall()
        setupClickListener()

    }

    private fun setupObserver() {

        viewModel.images.observe(this, Observer {
            when (it) {

                is IResult.Error -> {
                }
                is IResult.Success -> {
                    try {
                        it.data?.photos?.let {
                            val start = it.page.minus(1) * 10
                            val end = start + 9
                            val list = arrayListOf<Photo>()
                            val itemViewModelList = arrayListOf<ItemViewModel>()
                            it.photo.forEach {
                                itemViewModelList.add(ItemViewModel(it, itemViewModelRetrofit))
                            }
                            Imageadapter.setListData(itemViewModelList)


                        }
                    } catch (e: Exception) {
                    }
                }
                else -> {
//                    repeat(10)
//                    {
//                        imageAdapter.addItem(DefaultItem())
//                    }
                }
            }
        })

        viewModel.layoutManager.observe(this, Observer {
            manager = it.second
            setupLayoutManager()
        })

    }

    private fun setupScrollListener(managerType: Int) {
        when (managerType) {
            GRID_2 -> {
                viewModel.changeLayoutManager(GRID_2 to GridLayoutManager(this, 2))
            }
            GRID_3 -> {
                viewModel.changeLayoutManager(GRID_3 to GridLayoutManager(this, 3))
            }
            else -> {
                viewModel.changeLayoutManager(LIST to GridLayoutManager(this, 1))
            }
        }
    }

    private fun setupLayoutManager() {
        binding.rlvImages.apply {
            layoutManager = manager
            adapter = Imageadapter
        }
        endlessScrollListener = object : EndlessScrollListener(manager) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                recyclerView: RecyclerView
            ): Boolean {
                if (page == 0) {
                    requestModel.also { it.page = 1 }
                } else {
                    requestModel.also { it.page += 1 }
                }
                doApiCall()
                return false
            }

        }
        binding.rlvImages.addOnScrollListener(endlessScrollListener)
    }


    private fun setupClickListener() {
        binding.grid2.setOnClickListener {
            setupScrollListener(GRID_2)
        }
        binding.grid3.setOnClickListener {
            setupScrollListener(GRID_3)

        }
        binding.list.setOnClickListener {
            setupScrollListener(LIST)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }


    private fun doApiCall() {
        viewModel.getImages(requestModel)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}