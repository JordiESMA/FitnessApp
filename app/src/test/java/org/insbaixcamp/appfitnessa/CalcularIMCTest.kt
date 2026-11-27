package org.insbaixcamp.appfitnessa

import org.insbaixcamp.appfitnessa.tools.CalcularIMC
import org.junit.Assert.assertEquals
import org.junit.Test

class CalcularIMCTest {
    @Test
    fun calcularIMCPesNormal(){
        var pesActual = 75.0
        var alçada = 175
        var imc = CalcularIMC.calcularIMC(pesActual, alçada)
        assertEquals(24.49, imc,0.1)
    }
    @Test
    fun calcularIMCPesInferiorCero(){
        var pesActual = -1.0
        var alçada = 175
        var imc = CalcularIMC.calcularIMC(pesActual, alçada)
        assertEquals(0.0, imc,0.1)
    }
    @Test
    fun calcularIMCAlçadaInferiorCero(){
        var pesActual = 75.0
        var alçada = -1
        var imc = CalcularIMC.calcularIMC(pesActual, alçada)
        assertEquals(0.0, imc,0.1)
    }

    }
