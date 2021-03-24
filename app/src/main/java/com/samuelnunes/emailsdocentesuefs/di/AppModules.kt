package com.samuelnunes.emailsdocentesuefs.di

import android.content.ClipboardManager
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteAdminDAO
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteDAO
import com.samuelnunes.emailsdocentesuefs.repository.DocenteRepository
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.AdicionaOuEditaDocenteViewModel
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.ListDocentesFragmentViewModel
import com.samuelnunes.emailsdocentesuefs.ui.viewModel.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }
}

@ExperimentalCoroutinesApi
val daoModule = module {
    single<DocenteDAO> { DocenteDAO(get()) }
    single<DocenteAdminDAO> { (collection: String) -> DocenteAdminDAO(get(), collection) }
}

@ExperimentalCoroutinesApi
val repositoryModule = module {
    single<DocenteRepository> { DocenteRepository(get()) }
}

val uiModule = module {
}

val utilsModule = module {
    single<ClipboardManager?> {
        ContextCompat.getSystemService(
            get(),
            ClipboardManager::class.java
        )
    }
}

@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel<MainActivityViewModel> { MainActivityViewModel(get()) }
    viewModel<ListDocentesFragmentViewModel> { ListDocentesFragmentViewModel(get()) }
    viewModel<AdicionaOuEditaDocenteViewModel> { (id: String) ->
        AdicionaOuEditaDocenteViewModel(
            id,
            get()
        )
    }
}