package com.example.dia

// Define qué campos tiene un cupón según lo que vemos en la app
data class Cupon(
    val id: Int,
    val porcentaje: String, // Ej: "20% dto."
    val descripcion: String, // Ej: "COLACAO"
    val estaBloqueado: Boolean = true // Por defecto salen con candado
)