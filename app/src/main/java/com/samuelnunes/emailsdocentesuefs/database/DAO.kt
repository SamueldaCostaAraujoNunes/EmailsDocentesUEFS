package com.samuelnunes.emailsdocentesuefs.database

import com.google.firebase.firestore.CollectionReference
import com.samuelnunes.emailsdocentesuefs.model.Docente
import kotlinx.coroutines.flow.Flow

interface DAO<T> {
    val collection: CollectionReference
    suspend fun create(element: T): Docente?
    fun read(): Flow<List<Docente>>
    suspend fun delete(element: T): Docente?
}