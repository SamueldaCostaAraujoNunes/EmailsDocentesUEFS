package com.samuelnunes.emailsdocentesuefs.di

import android.content.ClipboardManager
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteCriadoDAO
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteEditadoDAO
import com.samuelnunes.emailsdocentesuefs.database.dao.DocentePublicoDAO
import com.samuelnunes.emailsdocentesuefs.repository.DocenteCriadoRepository
import com.samuelnunes.emailsdocentesuefs.repository.DocenteEditadoRepository
import com.samuelnunes.emailsdocentesuefs.repository.DocentePublicoRepository
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
    single<DocentePublicoDAO> { DocentePublicoDAO(get()) }
    single<DocenteEditadoDAO> { DocenteEditadoDAO(get()) }
    single<DocenteCriadoDAO> { DocenteCriadoDAO(get()) }
}

@ExperimentalCoroutinesApi
val repositoryModule = module {
    single<DocentePublicoRepository> { DocentePublicoRepository(get(), get(), get()) }
    single<DocenteEditadoRepository> { DocenteEditadoRepository(get(), get()) }
    single<DocenteCriadoRepository> { DocenteCriadoRepository(get(), get()) }
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
    viewModel<MainActivityViewModel> { MainActivityViewModel() }
    viewModel<ListDocentesFragmentViewModel> { (repository: DocenteRepository) ->
        ListDocentesFragmentViewModel(
            repository
        )
    }
    viewModel<AdicionaOuEditaDocenteViewModel> { (repository: DocenteRepository, id: String) ->
        AdicionaOuEditaDocenteViewModel(
            id,
            repository
        )
    }
}