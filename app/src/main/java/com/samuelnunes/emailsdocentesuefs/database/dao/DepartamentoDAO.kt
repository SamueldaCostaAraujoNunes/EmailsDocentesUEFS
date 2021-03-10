package com.samuelnunes.emailsdocentesuefs.database.dao

import androidx.lifecycle.LiveData
import com.samuelnunes.emailsdocentesuefs.model.Departamento

interface DepartamentoDAO {

    fun buscaTodos(): LiveData<List<Departamento>>
    fun salva(vararg departamento: Departamento)
    fun buscaPorId(id: Long): LiveData<Departamento>

}
