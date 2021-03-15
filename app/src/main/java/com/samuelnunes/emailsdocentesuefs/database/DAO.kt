package com.samuelnunes.emailsdocentesuefs.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samuelnunes.emailsdocentesuefs.model.Docente
import com.samuelnunes.emailsdocentesuefs.repository.Resource

interface DAO<T> {
    fun create(element: T)
    fun read(): LiveData<Resource<Set<Docente>>>
    fun update(element: T)
    fun delete(element: T)
}