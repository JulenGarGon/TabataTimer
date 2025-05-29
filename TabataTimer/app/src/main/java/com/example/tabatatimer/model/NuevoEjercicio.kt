package com.example.tabatatimer.model

class NuevoEjercicio (
    val idDocumento: String? = null,
    val id: Int? = null,
    val nombre: String? = null,
    val grupo_muscular: String? = null,
    val set: String? = null,
    val esfuerzo: Map<String, Int>? = null,
    val imagen: String? = null,
    val video: String? = null
)