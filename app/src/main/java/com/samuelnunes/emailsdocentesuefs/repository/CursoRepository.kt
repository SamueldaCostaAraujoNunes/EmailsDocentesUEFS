package com.samuelnunes.emailsdocentesuefs.repository

import androidx.lifecycle.LiveData
import com.samuelnunes.emailsdocentesuefs.database.dao.CursoDAO
import com.samuelnunes.emailsdocentesuefs.model.Curso

class CursoRepository(private val dao: CursoDAO) {

    fun buscaTodos(): LiveData<List<Curso>> = dao.buscaTodos()

    fun buscaPorId(id: Long): LiveData<Curso> = dao.buscaPorId(id)

}
