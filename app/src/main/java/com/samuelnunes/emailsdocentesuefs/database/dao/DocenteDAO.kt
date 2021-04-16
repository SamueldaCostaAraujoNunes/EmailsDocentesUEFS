package com.samuelnunes.emailsdocentesuefs.database.dao

import com.google.firebase.firestore.Query
import com.samuelnunes.emailsdocentesuefs.database.DAO
import com.samuelnunes.emailsdocentesuefs.extensions.asFlow
import com.samuelnunes.emailsdocentesuefs.extensions.await
import com.samuelnunes.emailsdocentesuefs.model.Docente
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@ExperimentalCoroutinesApi
interface DocenteDAO : DAO<Docente> {
    override fun read(): Flow<List<Docente>> {
        return collection
            .orderBy("name", Query.Direction.ASCENDING)
            .asFlow()
            .map { it.toObjects(Docente::class.java) }
    }

    override suspend fun create(element: Docente): Docente? {
        return try {
            val docenteAdicionado = collection.add(element).await()
            if (docenteAdicionado != null) {
                element.id = docenteAdicionado.id
            }
            element
        } catch (e: Exception) {
            null
        }
    }

    suspend fun update(element: Docente): Docente? {
        return try {
            collection
                .document(element.id.toString())
                .set(element)
                .await()
            element
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun delete(element: Docente): Docente? {
        return try {
            collection
                .document(element.id.toString())
                .delete()
                .await()
            element
        } catch (e: Exception) {
            null
        }
    }


}
