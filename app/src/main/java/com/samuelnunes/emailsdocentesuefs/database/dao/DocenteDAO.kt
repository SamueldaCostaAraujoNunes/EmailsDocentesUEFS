package com.samuelnunes.emailsdocentesuefs.database.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
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
    private val docenteLiveData: MutableLiveData<Resource<Docente>> = MutableLiveData()

    private val collection = firebase.collection(DOCENTES_PATH)

    init {
        firstRead()
        defineSnapshotsListeners()
    }

    private fun firstRead() {
        CoroutineScope(IO).launch {
            collection
                .get()
                .addOnSuccessListener { task ->
                    saveLocalDocentes { docentes ->
                        for (document in task.documents) {
                            convertDocente(document)?.let { docentes.add(it) }
                        }
                    }
                }
        }.start()
    }

    private fun defineSnapshotsListeners() {
        CoroutineScope(IO).launch {
            collection
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }
                    if (value != null) {
                        saveLocalDocentes { docentes ->
                            for (document in value.documents) {
                                convertDocente(document)?.let { docentes.add(it) }
                            }
                        }
                    }
                }
        }.start()
    }

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
                .add(createDocenteWithoutId(element))
                .addOnSuccessListener { documentReference ->
                    element.id = documentReference.id
                    saveLocalDocentes { docentes ->
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
        return docentesLiveData
    }

    private fun convertDocente(document: DocumentSnapshot): Docente? {
        val json = document.data
        val id = document.id

        return try {
            Docente(
                json!!["name"] as String,
                json["email"] as String,
                json["departmentCode"] as String,
                json["departmentName"] as String,
                id
            )
        } catch (e: TypeCastException) {
            Log.i("convertDocente: ", json.toString())
            null
        }
    }

    override fun update(element: Docente) {
        CoroutineScope(IO).launch {
            collection
                .document(element.id.toString())
                .set(createDocenteWithoutId(element))
                .addOnSuccessListener {
                    saveLocalDocentes { docentes ->
                        docentes.add(element)
                    }
                }
                .addOnFailureListener { e ->
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
                        saveLocalDocentes { docentes ->
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

    private fun saveLocalDocentes(action: (docentes: MutableSet<Docente>) -> Unit) {
        val docentes: MutableSet<Docente> =
            docentesLiveData.value?.dado?.toMutableSet() ?: mutableSetOf()
        action(docentes)
        docentesLiveData.value = Resource(dado = docentes.toSet())
    }


    private fun createDocenteWithoutId(d: Docente): Docente {
        return Docente(d.nome, d.email, d.departamentoCode, d.departamentoNome)
    }

    fun buscaPorId(id: String): LiveData<Resource<Docente>> {
        CoroutineScope(IO).launch {
            val docente = docentesLiveData.value?.dado?.find { docente -> docente.id == id }
            docenteLiveData.postValue(
                if (docente != null) {
                    Resource(dado = docente)
                } else {
                    val erro = "Não foi possivel encontrar o ID"
                    val resourceAtual = docenteLiveData.value
                    if (resourceAtual != null) {
                        Resource(dado = resourceAtual.dado, erro = erro)
                    } else
                        Resource(dado = null as Docente?, erro = erro)
                }
            )
        }.start()
        return docenteLiveData
    }
}