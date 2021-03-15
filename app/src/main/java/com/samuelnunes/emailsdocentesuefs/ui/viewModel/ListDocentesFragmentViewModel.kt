package com.samuelnunes.emailsdocentesuefs.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.repository.DocenteRepository
import com.samuelnunes.emailsdocentesuefs.repository.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ListDocentesFragmentViewModel(private val repository: DocenteRepository) : ViewModel() {

    fun buscaTodos(): LiveData<Resource<List<Docente>>> = repository.buscaTodos()
}
