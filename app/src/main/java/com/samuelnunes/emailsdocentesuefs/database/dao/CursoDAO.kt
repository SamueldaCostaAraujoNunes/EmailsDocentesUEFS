package com.samuelnunes.emailsdocentesuefs.database.dao

import androidx.lifecycle.LiveData
import com.samuelnunes.emailsdocentesuefs.model.Curso

interface CursoDAO {

    fun buscaTodos(): LiveData<List<Curso>>

    fun salva(vararg departamento: Curso)

    fun buscaPorId(id: Long): LiveData<Curso>

}