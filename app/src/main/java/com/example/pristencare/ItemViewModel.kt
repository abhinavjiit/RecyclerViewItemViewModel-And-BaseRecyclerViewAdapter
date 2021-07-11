package com.example.pristencare

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemViewModel : LifecycleObserver {
    lateinit var lifecycleRegistry: Lifecycle
    var position: Int = -1
    private var _timerValue = MutableLiveData<String>("0")
    val timerValue: LiveData<String>
        get() = _timerValue
    private var value = 0
    private var job: Job? = null
    private var fetched = false

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (::lifecycleRegistry.isInitialized) {
            lifecycleRegistry.coroutineScope.launchWhenCreated {
                Log.i("TAG", ": value==== $value -----------  position ============= $position  ")
                delay(1200)
                Log.i("TAG", ": value==== $value -----------  position +++++++++++++ $position  ")
                position += 1
                _timerValue.postValue(position.toString())
            }
        }
        Log.i("TAG", "onCreate:  position ====$position ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.i("TAG", "ON_START:position ====     $position ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.i("TAG", "ON_DESTROY: position ====     $position ")
        job?.cancel()
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
        job?.cancel()
    }
}