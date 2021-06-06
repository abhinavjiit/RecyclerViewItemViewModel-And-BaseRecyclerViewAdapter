package com.example.pristencare

import com.example.pristencare.activity.MainActivity
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    @ExperimentalCoroutinesApi
    fun inject(activity: MainActivity)
}