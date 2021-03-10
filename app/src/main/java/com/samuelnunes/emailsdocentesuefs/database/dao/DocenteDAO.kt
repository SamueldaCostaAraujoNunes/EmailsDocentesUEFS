package com.samuelnunes.emailsdocentesuefs.database.dao

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.samuelnunes.emailsdocentesuefs.model.Docente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

val DOCENTES_PATH = "docentes"

class DocenteDAO(private val firebase: FirebaseFirestore) : DAO<Docente> {
    private val __docentes: MutableLiveData<Set<Docente>> = MutableLiveData()
    private val collection = firebase.collection(DOCENTES_PATH)
    override val data: LiveData<Set<Docente>>
        get() = __docentes

    override fun create(docente: Docente) {
        if (docente.id == null) {
            add(docente)
        } else {
            update(docente)
        }
    }

    private fun add(docente: Docente) {
        CoroutineScope(IO).launch {
            collection
                .add(docente)
                .addOnSuccessListener { documentReference ->
                    docente.id = documentReference.id
                    saveLocal { docentes ->
                        docentes.add(docente)
                    }
                }
                .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }

        }.start()
    }

    override fun read(): LiveData<Set<Docente>> {
        CoroutineScope(IO).launch {
            collection
                .get()
                .addOnSuccessListener { task ->
                    saveLocal { docentes ->
                        for (document in task.documents) {
                            val json = document.data
                            val id = document.id
                            val docente = Docente(
                                json?.get("nome") as String,
                                json["email"] as String,
                                id
                            )
                            docentes.add(docente)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(
                        TAG,
                        "Error deleting document",
                        e
                    )
                }
        }.start()

        return __docentes
    }

    override fun update(docente: Docente) {
        CoroutineScope(IO).launch {
            collection
                .document(docente.id.toString())
                .set(createDocenteWithoutId(docente))
                .addOnSuccessListener {
                    saveLocal { docentes ->
                        docentes.add(docente)
                    }
                }
                .addOnFailureListener {

                }
        }.start()
    }

    override fun delete(docente: Docente) {
        CoroutineScope(IO).launch {
            if (docente.id != null) {
                collection.document(docente.id!!)
                    .delete()
                    .addOnSuccessListener {
                        saveLocal { docentes ->
                            docentes.remove(docente)
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(
                            TAG,
                            "Error deleting document",
                            e
                        )
                    }
            } else {
                Log.w(
                    TAG,
                    "Error deleting document"
                )
            }
        }.start()
    }

    private fun saveLocal(action: (docentes: MutableSet<Docente>) -> Unit) {
        val docentes: MutableSet<Docente> = __docentes.value?.toMutableSet() ?: mutableSetOf()
        action(docentes)
        __docentes.value = docentes.toSet()
    }

    private fun createDocenteWithoutId(d: Docente): Docente {
        return Docente(d.nome, d.email)
    }
}