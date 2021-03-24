package com.samuelnunes.emailsdocentesuefs.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.repository.DocenteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class AdicionaOuEditaDocenteViewModel(
    private val docenteId: String?,
    private val repository: DocenteRepository
) : ViewModel() {

    val docente: Docente = docenteId?.let { repository.buscaPorId(it) } ?: Docente()

    fun createDocente(docente: Docente) {
        viewModelScope.launch { repository.addDocente(docente) }
    }

}