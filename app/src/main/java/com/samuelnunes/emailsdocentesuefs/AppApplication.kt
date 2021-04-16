package com.samuelnunes.emailsdocentesuefs

import android.app.Application
import com.samuelnunes.emailsdocentesuefs.di.*
import com.samuelnunes.emailsdocentesuefs.util.CrashlyticsReportingTree
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@ExperimentalCoroutinesApi
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(
                listOf(
                    databaseModule,
                    daoModule,
                    repositoryModule,
                    utilsModule,
                    uiModule,
                    viewModelModule
                )
            )
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsReportingTree())
        }
    }
}