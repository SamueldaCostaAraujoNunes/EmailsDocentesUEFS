package com.samuelnunes.emailsdocentesuefs.repository

import androidx.lifecycle.LiveData
import com.samuelnunes.emailsdocentesuefs.database.dao.DepartamentoDAO
import com.samuelnunes.emailsdocentesuefs.model.Departamento

class DepartmentoRepository(private val dao: DepartamentoDAO) {

    fun buscaTodos(): LiveData<List<Departamento>> = dao.buscaTodos()

    fun buscaPorId(id: Long): LiveData<Departamento> = dao.buscaPorId(id)

}
