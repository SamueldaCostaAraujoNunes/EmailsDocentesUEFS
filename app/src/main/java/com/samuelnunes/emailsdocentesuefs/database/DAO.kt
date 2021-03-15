package com.samuelnunes.emailsdocentesuefs.database

import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.repository.Resource
import kotlinx.coroutines.flow.Flow

interface DAO<T> {
    suspend fun create(element: T)
    suspend fun read(): Flow<Resource<List<Docente>>>
    suspend fun delete(element: T): Docente?
}