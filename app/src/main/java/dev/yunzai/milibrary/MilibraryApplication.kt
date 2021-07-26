package dev.yunzai.milibrary

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dev.yunzai.milibrary.di.netWorkModule
import dev.yunzai.milibrary.di.repositoryModule
import dev.yunzai.milibrary.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MilibraryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this@MilibraryApplication).build()
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        startKoin {
            androidContext(this@MilibraryApplication)
            modules(netWorkModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }
    }

    companion object {
        @JvmStatic
        val instance = this
    }
}
