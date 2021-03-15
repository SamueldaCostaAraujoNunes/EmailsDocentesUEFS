package com.samuelnunes.emailsdocentesuefs.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteDAO
import com.samuelnunes.emailsdocentesuefs.model.Docente
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking


@ExperimentalCoroutinesApi
class DocenteRepository(private val dao: DocenteDAO) {

    private val docentes: LiveData<Resource<List<Docente>>> by lazy {
        runBlocking {
            dao.read().asLiveData()
        }
    }

    fun buscaTodos(): LiveData<Resource<List<Docente>>> = docentes
}
