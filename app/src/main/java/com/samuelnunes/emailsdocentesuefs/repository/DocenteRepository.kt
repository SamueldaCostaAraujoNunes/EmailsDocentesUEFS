package com.samuelnunes.emailsdocentesuefs.repository

import androidx.lifecycle.LiveData
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteDAO
import com.samuelnunes.emailsdocentesuefs.model.Docente


class DocenteRepository(private val dao: DocenteDAO) {

    fun addDocente(docente: Docente) = dao.create(docente)

    fun buscaTodos() : LiveData<Resource<Set<Docente>>> = dao.read()

    fun buscaPorId(id: String): LiveData<Resource<Docente>> = dao.buscaPorId(id)

}
