package uz.devapp.uzchat

import android.app.Application
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {
    companion object{
        lateinit var app: MyApp
    }
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        app = this
    }
}