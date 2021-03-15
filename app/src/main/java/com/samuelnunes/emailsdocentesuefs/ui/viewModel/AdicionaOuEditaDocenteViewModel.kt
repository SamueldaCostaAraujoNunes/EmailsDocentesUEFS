package com.samuelnunes.emailsdocentesuefs.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.repository.DocenteRepository
import com.samuelnunes.emailsdocentesuefs.repository.Resource

class AdicionaOuEditaDocenteViewModel(
    docenteId: String,
    private val repository: DocenteRepository
) : ViewModel() {

    val docente: LiveData<Resource<Docente>> = repository.buscaPorId(docenteId)
    fun addDocente(docente: Docente) = repository.addDocente(docente)

}