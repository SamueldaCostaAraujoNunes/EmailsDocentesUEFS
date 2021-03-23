package com.samuelnunes.emailsdocentesuefs.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteDAO
import com.samuelnunes.emailsdocentesuefs.model.Docente
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class DocenteRepository(private val dao: DocenteDAO) {

    private val data = dao.read().asLiveData()

    fun buscaTodos(): LiveData<List<Docente>> = data
    fun buscaPorId(docenteId: String): Docente? {
        return data.value?.find { docente -> docente.id == docenteId }
    }
}
