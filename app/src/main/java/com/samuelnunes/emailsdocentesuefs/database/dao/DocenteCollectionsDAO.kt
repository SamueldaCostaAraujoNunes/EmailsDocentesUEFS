package com.samuelnunes.emailsdocentesuefs.database.dao

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.samuelnunes.emailsdocentesuefs.database.DOCENTES_COLLECTION
import com.samuelnunes.emailsdocentesuefs.database.DOCENTES_CRIADOS_COLLECTION
import com.samuelnunes.emailsdocentesuefs.database.DOCENTES_EDITADOS_COLLECTION
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DocentePublicoDAO(firebase: FirebaseFirestore) : DocenteDAO {
    override val collection: CollectionReference = firebase.collection(DOCENTES_COLLECTION)
}

@ExperimentalCoroutinesApi
class DocenteEditadoDAO(firebase: FirebaseFirestore) : DocenteDAO {
    override val collection: CollectionReference = firebase.collection(DOCENTES_EDITADOS_COLLECTION)
}

@ExperimentalCoroutinesApi
class DocenteCriadoDAO(firebase: FirebaseFirestore) : DocenteDAO {
    override val collection: CollectionReference = firebase.collection(DOCENTES_CRIADOS_COLLECTION)
}