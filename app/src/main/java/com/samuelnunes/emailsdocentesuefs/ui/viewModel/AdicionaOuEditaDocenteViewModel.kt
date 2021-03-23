package com.samuelnunes.emailsdocentesuefs.ui.viewModel

import androidx.lifecycle.ViewModel
import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.repository.DocenteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

class AdicionaOuEditaDocenteViewModel(
    docenteId: String,
    private val repository: DocenteRepository
) : ViewModel() {

    @ExperimentalCoroutinesApi
    val docente: Docente? = repository.buscaPorId(docenteId)
//    fun addDocente(docente: Docente) = repository.addDocente(docente)

}