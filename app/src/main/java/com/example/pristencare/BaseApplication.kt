package com.example.pristencare

import android.app.Application

class BaseApplication : Application(), Injector {

    private lateinit var applicationComponent: AppComponent

    companion object {
        @JvmField
        var appContext: BaseApplication? = null

        fun setInstance(application: BaseApplication) {
            appContext = application
        }

        @JvmStatic
        fun getInstance(): BaseApplication {
            return appContext as BaseApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        setInstance(this)
        applicationComponent = DaggerAppComponent.create()
    }


    override fun createAppComponent(): AppComponent {
        return applicationComponent
    }

}