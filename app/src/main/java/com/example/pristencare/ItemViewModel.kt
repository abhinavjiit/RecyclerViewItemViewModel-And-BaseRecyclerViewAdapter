package com.example.pristencare

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class ItemViewModel : LifecycleOwner, LifecycleObserver {
    lateinit var lifecycleRegistry: LifecycleRegistry
    private var _timerValue = MutableLiveData<String>("0")
    val timerValue: LiveData<String>
        get() = _timerValue
    var value = 1
    private var job: Job? = null

    init { }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.i("TAG", "onCreate: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.i("TAG", "onCreate: ")
       lifecycle.coroutineScope.launchWhenStarted {
            delay(10000)
            Log.i("TAG", ": $value")
            _timerValue.postValue(value.toString())
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.i("TAG", "ON_DESTROY: ")
        job?.cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.i("TAG", "ON_STOP: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.i("TAG", "ON_PAUSE: ")

    }

    fun doCancel(position: Int) {
        Log.i("TAG", "doCancel:$position ")
        job?.cancel()
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}