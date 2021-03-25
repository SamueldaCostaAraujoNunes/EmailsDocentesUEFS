package com.samuelnunes.emailsdocentesuefs.model

import com.google.firebase.firestore.DocumentId

data class Docente(
    val name: String? = null,
    val email: String? = null,
    val departmentCode: String? = null,
    var departmentName: String? = null,
    @DocumentId
    var id: String? = null
) {
    constructor() : this(
        name = null,
        email = null,
        departmentCode = null,
        departmentName = null,
        id = null
    )
}
