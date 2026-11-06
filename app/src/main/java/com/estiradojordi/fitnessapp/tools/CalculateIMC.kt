package com.estiradojordi.fitnessapp.tools

fun calculateIMC(pes: Double, alcada: Int): Double {
    if (pes <= 0.0) {
        throw IllegalArgumentException("El pes deu ser major que 0")
    }
    if (alcada <= 0) {
        throw IllegalArgumentException("L'alçada deu ser mayor que 0")
    }
    val alcadaMetres = alcada / 100.0
    return pes / (alcadaMetres * alcadaMetres)
}