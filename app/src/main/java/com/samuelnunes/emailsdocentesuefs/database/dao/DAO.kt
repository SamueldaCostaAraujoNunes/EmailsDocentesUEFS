package com.samuelnunes.emailsdocentesuefs.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface DAO<T> {

    val data: LiveData<Set<T>>

    fun create(t: T)
    fun read(): LiveData<Set<T>>
    fun update(t: T)
    fun delete(t: T)
}