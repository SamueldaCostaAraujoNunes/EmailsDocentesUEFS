package com.samuelnunes.emailsdocentesuefs.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.repository.DocenteRepository
import com.samuelnunes.emailsdocentesuefs.repository.Resource

class AdicionaDocenteViewModel(private val repository: DocenteRepository) : ViewModel() {

    fun addDocente(docente: Docente) = repository.addDocente(docente)
}