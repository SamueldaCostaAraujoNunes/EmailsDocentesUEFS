package com.samuelnunes.emailsdocentesuefs.di

import com.google.firebase.firestore.FirebaseFirestore
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteDAO
import com.samuelnunes.emailsdocentesuefs.repository.DocenteRepository
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.MainActivityViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }
}

val daoModule = module {
    single<DocenteDAO> { DocenteDAO(get()) }
}

val repositoryModule = module {
    single<DocenteRepository> { DocenteRepository(get()) }
}

val uiModule = module {
}

val viewModelModule = module {
    viewModel<MainActivityViewModel> { MainActivityViewModel(get()) }
}