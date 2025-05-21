package com.example.tabatatimer.model

data class EjercicioRealizado (
    val id: String ?= null,
    val ejercicio: String? = null,
    val peso: Float? = null,
    val repeticiones: Int? = null
)