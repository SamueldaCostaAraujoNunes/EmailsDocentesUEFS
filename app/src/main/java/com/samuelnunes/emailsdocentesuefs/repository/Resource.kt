package com.samuelnunes.emailsdocentesuefs.repository

class Resource<out T>(val status: Status, val data: T?, private val exception: Exception?) {

    enum class Status {
        SUCCESS, ERROR, LOADING, CANCEL
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(exception: Exception? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                exception
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> cancel(data: T? = null, exception: Exception? = null): Resource<T> {
            return Resource(
                Status.CANCEL,
                data,
                exception
            )
        }
    }

    fun getErrorMessage() = exception?.localizedMessage
}