package com.samuelnunes.emailsdocentesuefs.database

import com.samuelnunes.emailsdocentesuefs.model.Docente
import kotlinx.coroutines.flow.Flow

interface DAO<T> {
    suspend fun create(element: T)
    fun read(): Flow<List<Docente>>
    suspend fun delete(element: T): Docente?
}