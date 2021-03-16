package com.samuelnunes.emailsdocentesuefs.extensions

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun DocumentReference.asFlow(reportExceptions: Boolean = true) = callbackFlow {
    val subscription = this@asFlow.addSnapshotListener { value, error ->
        if (value != null) sendBlocking(value)
        else if (error != null && reportExceptions) cancel("firestore error", error)
    }
    awaitClose { subscription.remove() }
}

@ExperimentalCoroutinesApi
fun CollectionReference.asFlow(reportExceptions: Boolean = true) = callbackFlow {
    val subscription = this@asFlow.addSnapshotListener { value, error ->
        if (value != null) sendBlocking(value)
        else if (error != null && reportExceptions) cancel("firestore error", error)
    }
    awaitClose { subscription.remove() }
}

@ExperimentalCoroutinesApi
fun Query.asFlow(reportExceptions: Boolean = true) = callbackFlow {
    val subscription = this@asFlow.addSnapshotListener { value, error ->
        if (value != null) sendBlocking(value)
        else if (error != null && reportExceptions) cancel("firestore error", error)
    }
    awaitClose { subscription.remove() }
}