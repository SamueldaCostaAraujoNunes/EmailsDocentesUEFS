package com.samuelnunes.emailsdocentesuefs

import android.app.Application
import com.samuelnunes.emailsdocentesuefs.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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
    }
}