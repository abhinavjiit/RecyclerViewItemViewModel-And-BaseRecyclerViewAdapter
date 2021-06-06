package com.example.pristencare

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pristencare.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

const val LIST = 0
const val GRID_2 = 1
const val GRID_3 = 2

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val imageAdapter by lazy { ImageRecyclerViewAdapter() }

    private lateinit var endlessScrollListener: EndlessScrollListener

    private var manager = GridLayoutManager(this, 1)

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewMOdelFactory(RepositoryImpl(retrofit))
    }

    private var requestModel = RequestModel()

    @Inject
    lateinit var retrofit: ApiService

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

                is IResult.Error -> { }
                is IResult.Success -> {
                    it.data?.photos?.photo?.forEach { photo ->
                        imageAdapter.addItem(ImageItem(photo))
                    }
                }
                else -> { }
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
            adapter = imageAdapter
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


    private fun doApiCall() {
        viewModel.getImages(requestModel)
    }

}