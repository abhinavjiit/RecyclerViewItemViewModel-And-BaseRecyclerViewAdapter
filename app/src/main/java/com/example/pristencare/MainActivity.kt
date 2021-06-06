package com.example.pristencare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pristencare.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val LIST = 0
const val GRID_2 = 1
const val GRID_3 = 2

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val imageAdapter = ImageRecyclerViewAdapter()

    private lateinit var endlessScrollListener: EndlessScrollListener

    private var manager = LinearLayoutManager(this)

    private lateinit var viewModel: MainActivityViewModel

    private var requestModel = RequestModel()

    private val mainActivityViewModelFactory by lazy {
        MainActivityViewMOdelFactory(
            RepositoryImpl(
                Retrofit.getRetrofit().create(ApiService::class.java)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.rlvImages.adapter = imageAdapter

        repeat(10) {
            imageAdapter.addItem(ImageItem())
        }


//        setupViewModel()
//        setupObserver()


//        viewModel.getImages(requestModel)
//        setupClickListener()

    }

//    private fun setupViewModel() {
//        viewModel =
//            ViewModelProvider(this, mainActivityViewModelFactory)[MainActivityViewModel::class.java]
//    }

//    private fun setupObserver() {
//
//
//        viewModel.images.observe(this, Observer {
//            when (it) {
//
//                is IResult.Error -> {
//                }
//                is IResult.Success -> {
//                    it.data?.photos?.photo?.forEach { photo ->
//                        imageAdapter.addItem(ImageItem())
//                    }
//                }
//                else -> {
//                }
//
//            }
//        })
//
//
////        viewModel.layoutManager.observe(this, Observer {
////            manager = it.second
////            setupLayoutManager()
////        })
//
//    }


//    private fun setupScrollListener(managerType: Int) {
////        when (managerType) {
////            1 -> {
////                viewModel.changeLayoutManager(GRID_2 to GridLayoutManager(this, 2))
////            }
////            2 -> {
////                viewModel.changeLayoutManager(GRID_3 to GridLayoutManager(this, 3))
////            }
////            else -> {
////                viewModel.changeLayoutManager(LIST to GridLayoutManager(this, 1))
////            }
////        }
//    }

//    private fun setupLayoutManager() {
////        endlessScrollListener = object : EndlessScrollListener(manager) {
////            override fun onLoadMore(
////                page: Int,
////                totalItemsCount: Int,
////                recyclerView: RecyclerView
////            ): Boolean {
////                if (page == 0) {
////                    requestModel.also { it.page = 1 }
////                } else {
////                    requestModel.also { it.page += 1 }
////                }
////                doApiCall()
////                return false
////            }
////
////        }
////        binding.rlvImages.addOnScrollListener(endlessScrollListener)
//    }


//    private fun setupClickListener() {
//        binding.grid2.setOnClickListener {
//            setupScrollListener(GRID_2)
//        }
//        binding.grid3.setOnClickListener {
//            setupScrollListener(GRID_3)
//
//        }
//        binding.list.setOnClickListener {
//            setupScrollListener(LIST)
//        }
//
//
//    }


}