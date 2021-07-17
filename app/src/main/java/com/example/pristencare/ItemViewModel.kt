package com.example.pristencare

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.pristencare.apiservice.ApiService
import com.example.pristencare.domain.RepositoryImpl
import com.example.pristencare.model.Photo
import com.example.pristencare.model.PhotoDetail
import com.example.pristencare.utils.IResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.math.log

@ExperimentalCoroutinesApi
class ItemViewModel @Inject constructor(
    private val image: Photo,
    private val retrofit: ApiService
) : LifecycleObserver {

    lateinit var lifecycleRegistry: Lifecycle

    var position: Int = -1

    private var _timerValue = MutableLiveData<String>("0")
    val timerValue: LiveData<String>
        get() = _timerValue

    private var value = 0
    private var job: Job? = null
    private var fetched = false

    private var _photo = MutableLiveData<String>("https://via.placeholder.com/600/92c952")
    val photo: LiveData<String> = _photo

    private var _loading = MutableLiveData<IResult<PhotoDetail>>()

    init {
//        retrofit = Retrofit.Builder()
//            .baseUrl("https://jsonplaceholder.typicode.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

        Log.i("TAG", "onCreate:  position ====$position ")


    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (::lifecycleRegistry.isInitialized) {
            lifecycleRegistry.coroutineScope.launchWhenCreated {

                //TODO(need to hit a api call to update the data thorough databinding)

                //https://jsonplaceholder.typicode.com/photos

                if (!fetched) {

                    Log.i("TAG", "onResume: (((((((((((((((((( $position )))))))))))))) ")

                    RepositoryImpl(retrofit).getPhotoDetail(position+1)
                        .catch {
                            _loading.postValue(IResult.Error(it))

                        }.collect {
                            when (it) {
                                is IResult.Success -> {
                                    Log.i(
                                        "TAG",
                                        "IResult.Success: (((((((((((((((((( $position )))))))))))))) "
                                    )
                                    fetched = true
                                    _photo.postValue(it.data?.url)
                                    _loading.postValue(it)
                                }
                                is IResult.Error -> {
                                    _loading.postValue(IResult.Error(it.throwable))

                                }
                                else -> {
                                    _loading.postValue(IResult.Loading)
                                }
                            }
                        }
                }

//                if (!fetched) {
//                    fetched = true
//                    val url =
//                        "https://live.staticflickr.com/" + image.server + "/" + image.id + "_" + image.secret + "_" + "w.jpg"
//                    _photo.postValue(url)
//                    Log.i(
//                        "TAG",
//                        ": value==== $value -----------  position ============= $position  "
//                    )
//                    delay(1200)
//                    value += 1
//                    _timerValue.value = value.toString()
//                    Log.i(
//                        "TAG",
//                        ": value==== $value -----------  position +++++++++++++ $position  "
//                    )
//                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.i("TAG", "ON_START:position ====     $position ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.i("TAG", "ON_DESTROY: position ====     $position ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.i("TAG", "ON_STOP:position ====        $position ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.i("TAG", "ON_PAUSE:position ====        $position ")

    }

    fun doCancel(position: Int) {
        Log.i("TAG", "doCancel:position ====  $position ")
    }


    fun onClick(v: View, url: String) {
        lifecycleRegistry.coroutineScope.launchWhenResumed {
            Log.i("TAG", "onClick:                   $position")
            delay(1500)
            value += 1
            _timerValue.value = value.toString()
        }

//        v.context.launchActivity<NextActivity> {
//            putExtra("url", url)
//        }
    }
}