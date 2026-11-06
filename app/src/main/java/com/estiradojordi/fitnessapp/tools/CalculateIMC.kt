package com.estiradojordi.fitnessapp.tools

fun calculateIMC(pes: Double, alcada: Int): Double {
    val alcadaMetres = alcada / 100.0
    return pes / (alcadaMetres * alcadaMetres)
}