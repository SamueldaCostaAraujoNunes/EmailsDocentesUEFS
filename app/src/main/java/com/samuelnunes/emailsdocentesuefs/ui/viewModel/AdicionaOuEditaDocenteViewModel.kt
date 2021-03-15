package com.samuelnunes.emailsdocentesuefs.ui.viewModel

import androidx.lifecycle.ViewModel
import com.samuelnunes.emailsdocentesuefs.repository.DocenteRepository

class AdicionaOuEditaDocenteViewModel(
    docenteId: String,
    private val repository: DocenteRepository
) : ViewModel() {

//    val docente: LiveData<Resource<Docente>> = repository.buscaPorId(docenteId)
//    fun addDocente(docente: Docente) = repository.addDocente(docente)

}