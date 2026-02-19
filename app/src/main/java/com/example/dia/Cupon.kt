package com.example.dia

data class Cupon(
    val id: Int,
    val porcentaje: String,
    val descripcion: String,
    val estaBloqueado: Boolean = true
)