package com.example.tabatatimer.model

data class Ejercicio(
    val id: Int? = null,
    val nombre: String? = null,
    //val grupo_muscular: Musculos? = null,
    val grupo_muscular: String? = null,
    //val set: Sets? = null,
    val set: String? = null,
    val esfuerzo: Map<String, Int>? = null,
    val imagen: String? = null,
    val video: String? = null
)
