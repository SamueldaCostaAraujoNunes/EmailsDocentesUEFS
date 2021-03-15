package com.samuelnunes.emailsdocentesuefs.model

data class Docente(
    val nome: String,
    val email: String,
    val departamentoCode: String,
    val departamentoNome: String,
    var id: String? = null
)