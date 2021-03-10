package com.samuelnunes.emailsdocentesuefs.repository

import androidx.lifecycle.LiveData
import com.samuelnunes.emailsdocentesuefs.database.dao.DocenteDAO
import com.samuelnunes.emailsdocentesuefs.model.Docente


class DocenteRepository(private val dao: DocenteDAO) {

    fun buscaTodos() : LiveData<Resource<Set<Docente>>> = dao.read()

    fun buscaPorId(id: String): Docente? = dao.buscaPorId(id)

}
