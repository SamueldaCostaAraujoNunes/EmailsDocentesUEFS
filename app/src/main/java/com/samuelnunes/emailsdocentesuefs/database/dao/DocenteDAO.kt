package com.samuelnunes.emailsdocentesuefs.database.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.samuelnunes.emailsdocentesuefs.database.DAO
import com.samuelnunes.emailsdocentesuefs.extensions.await
import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.repository.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


const val DOCENTES_PATH = "docentes"

@ExperimentalCoroutinesApi
class DocenteDAO(firebase: FirebaseFirestore) : DAO<Docente> {
    private val collection = firebase.collection(DOCENTES_PATH)

    override suspend fun read() = flow<Resource<List<Docente>>> {
        try {
            emit(Resource.loading())
            val snapshot = collection.get().await()
            val docentes = mutableListOf<Docente>()
            if (snapshot != null) {
                for (document in snapshot.documents) {
                    document.toObject(Docente::class.java)?.let { docentes.add(it) }
                }
            }
            docentes.sortBy { it.name }
            emit(Resource.success(docentes))
        } catch (e: Exception) {
            emit(Resource.error(e))
        }
    }.flowOn(IO)

    override suspend fun create(element: Docente) {
        if (element.id == null) {
            add(element)
        } else {
            update(element)
        }
    }

    private suspend fun add(element: Docente): Docente? {
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

    private suspend fun update(element: Docente): Docente? {
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
