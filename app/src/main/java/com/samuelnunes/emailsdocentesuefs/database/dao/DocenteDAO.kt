package com.samuelnunes.emailsdocentesuefs.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.samuelnunes.emailsdocentesuefs.database.DAO
import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.repository.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

const val DOCENTES_PATH = "docentes"

class DocenteDAO(firebase: FirebaseFirestore) : DAO<Docente> {
    private val docentesLiveData: MutableLiveData<Resource<Set<Docente>>> = MutableLiveData()

    private val collection = firebase.collection(DOCENTES_PATH)
    override val data: LiveData<Resource<Set<Docente>>>
        get() = docentesLiveData

    override fun create(element: Docente) {
        if (element.id == null) {
            add(element)
        } else {
            update(element)
        }
    }

    private fun add(element: Docente) {
        CoroutineScope(IO).launch {
            collection
                .add(element)
                .addOnSuccessListener { documentReference ->
                    element.id = documentReference.id
                    saveLocal { docentes ->
                        docentes.add(element)
                    }
                }
                .addOnFailureListener { e ->
                    val erro = e.toString()
                    criaResourceDeFalha(erro)
                }

        }.start()
    }

    override fun read(): LiveData<Resource<Set<Docente>>> {
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
                    val erro = e.toString()
                    criaResourceDeFalha(erro)
                }
        }.start()

        return docentesLiveData
    }

    override fun update(element: Docente) {
        CoroutineScope(IO).launch {
            collection
                .document(element.id.toString())
                .set(createDocenteWithoutId(element))
                .addOnSuccessListener {
                    saveLocal { docentes ->
                        docentes.add(element)
                    }
                }
                .addOnFailureListener { e->
                    val erro = e.toString()
                    criaResourceDeFalha(erro)
                }
        }.start()
    }

    override fun delete(element: Docente) {
        CoroutineScope(IO).launch {
            if (element.id != null) {
                collection.document(element.id!!)
                    .delete()
                    .addOnSuccessListener {
                        saveLocal { docentes ->
                            docentes.remove(element)
                        }
                    }
                    .addOnFailureListener { e ->
                        val erro = e.toString()
                        criaResourceDeFalha(erro)
                    }
            } else {
                criaResourceDeFalha("Docente sem ID")
            }
        }.start()
    }


    private fun criaResourceDeFalha(erro: String) {
        val resourceAtual = docentesLiveData.value
        docentesLiveData.value = if (resourceAtual != null) {
            Resource(dado = resourceAtual.dado, erro = erro)
        } else Resource(dado = null, erro = erro)
    }

    private fun saveLocal(action: (docentes: MutableSet<Docente>) -> Unit) {
        val docentes: MutableSet<Docente> = docentesLiveData.value?.dado?.toMutableSet() ?: mutableSetOf()
        action(docentes)
        docentesLiveData.value = Resource(dado = docentes.toSet())
    }

    private fun createDocenteWithoutId(d: Docente): Docente {
        return Docente(d.nome, d.email)
    }

    fun buscaPorId(id: String): Docente? {
        return docentesLiveData.value?.dado?.find { docente -> docente.id == id }
    }
}