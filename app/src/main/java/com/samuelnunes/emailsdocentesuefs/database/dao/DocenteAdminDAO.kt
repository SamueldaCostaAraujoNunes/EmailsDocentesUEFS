package com.samuelnunes.emailsdocentesuefs.database.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.samuelnunes.emailsdocentesuefs.database.DAO
import com.samuelnunes.emailsdocentesuefs.database.DOCENTES_COLLECTION
import com.samuelnunes.emailsdocentesuefs.extensions.asFlow
import com.samuelnunes.emailsdocentesuefs.extensions.await
import com.samuelnunes.emailsdocentesuefs.model.Docente
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class DocenteAdminDAO(firebase: FirebaseFirestore, collection: String) : DAO<Docente> {
    private val collectionDocentesAprovados = firebase.collection(DOCENTES_COLLECTION)
    private val collection = firebase.collection(collection)

    override fun read(): Flow<List<Docente>> {
        return collection
            .orderBy("name", Query.Direction.ASCENDING)
            .asFlow()
            .map { it.toObjects(Docente::class.java) }
    }

    override suspend fun create(element: Docente) {
        update(element)
    }

    private suspend fun update(element: Docente): Docente? {
        return try {
            collectionDocentesAprovados
                .document(element.id.toString())
                .set(element)
                .await()
            delete(element)
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
