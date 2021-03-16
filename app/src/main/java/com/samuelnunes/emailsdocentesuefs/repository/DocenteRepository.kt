package com.samuelnunes.emailsdocentesuefs.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteDAO
import com.samuelnunes.emailsdocentesuefs.model.Docente
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class DocenteRepository(private val dao: DocenteDAO) {


    fun buscaTodos(): LiveData<List<Docente>> = dao.read().asLiveData()
}
