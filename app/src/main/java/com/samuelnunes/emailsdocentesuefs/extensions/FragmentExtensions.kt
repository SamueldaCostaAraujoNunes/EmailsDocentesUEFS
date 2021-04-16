package com.samuelnunes.emailsdocentesuefs.ui.fragment

import androidx.fragment.app.Fragment
import com.samuelnunes.emailsdocentesuefs.repository.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

@OptIn(ExperimentalCoroutinesApi::class)
fun Fragment.selectRepository(typeList: String?): DocenteRepository {
    return when (typeList) {
        REPOSITORY_PUBLIC -> {
            val repo: DocentePublicoRepository by inject()
            repo
        }
        REPOSITORY_ADMIN_EDITAR -> {
            val repo: DocenteEditadoRepository by inject()
            repo
        }
        REPOSITORY_ADMIN_SALVAR -> {
            val repo: DocenteCriadoRepository by inject()
            repo
        }
        else -> {
            val repo: DocentePublicoRepository by inject()
            repo
        }
    }
}

@ExperimentalCoroutinesApi
fun Fragment.filter(text: String?) {
    when (this) {
        is ListDocentesFragment -> {
            adapter.filter(text)
        }
        is AllListOfDocentesFragment -> {
            currentFragment().adapter.filter(text)
        }
    }
}