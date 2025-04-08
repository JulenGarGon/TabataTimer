package com.example.tabatatimer.model

data class Ejercicio(
    val id: Int? = null,
    val nombre: String? = null,
    val grupo_muscular: Musculos? = null,
    val set: Sets? = null,
    val esfuerzo: Map<String, Int>? = null,
    val url_imagen: String? = null,
    val url_video: String? = null
)
