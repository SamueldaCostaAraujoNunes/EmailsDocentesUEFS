package com.samuelnunes.emailsdocentesuefs.extensions

import java.text.Normalizer

fun String.unaccent(): String {
    return removeAccents(this)
}

fun removeAccents(src: String): String {
    return Normalizer
        .normalize(src, Normalizer.Form.NFD)
        .replace(Regex("[^\\p{ASCII}]"), "")
}