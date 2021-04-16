package com.samuelnunes.emailsdocentesuefs.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteCriadoDAO
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteEditadoDAO
import com.samuelnunes.emailsdocentesuefs.database.dao.DocentePublicoDAO
import com.samuelnunes.emailsdocentesuefs.model.Docente
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface DocenteRepository {

    val data: LiveData<List<Docente>>
    fun buscaTodos(): LiveData<List<Docente>> = data
    fun buscaPorId(docenteId: String): Docente? {
        return data.value?.find { docente -> docente.id == docenteId }
    }

    suspend fun addDocente(docente: Docente)
}

@ExperimentalCoroutinesApi
class DocentePublicoRepository(
    private val docentePublicoDAO: DocentePublicoDAO,
    private val docenteCriadoDAO: DocenteCriadoDAO,
    private val docenteEditadoDAO: DocenteEditadoDAO
) : DocenteRepository {

    override val data: LiveData<List<Docente>> = docentePublicoDAO.read().asLiveData()

    override suspend fun addDocente(docente: Docente) {
        if (docente.id == null) {
            docenteCriadoDAO.create(docente)
        } else {
            docenteEditadoDAO.update(docente)
        }
    }
}

@ExperimentalCoroutinesApi
class DocenteEditadoRepository(
    private val docentePublicoDAO: DocentePublicoDAO,
    private val docenteEditadoDAO: DocenteEditadoDAO
) : DocenteRepository {

    override val data: LiveData<List<Docente>> = docenteEditadoDAO.read().asLiveData()

    override suspend fun addDocente(docente: Docente) {
        docentePublicoDAO.update(docente)
        docenteEditadoDAO.delete(docente)
    }
}

@ExperimentalCoroutinesApi
class DocenteCriadoRepository(
    private val docentePublicoDAO: DocentePublicoDAO,
    private val docenteCriadoDAO: DocenteCriadoDAO
) : DocenteRepository {

    override val data: LiveData<List<Docente>> = docenteCriadoDAO.read().asLiveData()

    override suspend fun addDocente(docente: Docente) {
        docentePublicoDAO.update(docente)
        docenteCriadoDAO.delete(docente)
    }
}