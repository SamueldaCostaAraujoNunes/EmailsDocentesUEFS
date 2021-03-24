package com.samuelnunes.emailsdocentesuefs.extensions

import android.util.Patterns
import java.text.Normalizer

fun String.unaccent(): String {
    return removeAccents(this)
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

private fun removeAccents(src: String): String {
    return Normalizer
        .normalize(src, Normalizer.Form.NFD)
        .replace(Regex("[^\\p{ASCII}]"), "")
}