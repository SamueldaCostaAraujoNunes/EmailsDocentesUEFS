package com.samuelnunes.emailsdocentesuefs.model

data class Docente(
    val nome: String,
    val email: String,
    var id: String? = null
)