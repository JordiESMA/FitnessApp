package com.estiradojordi.fitnessapp.tools

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CalculateIMCTest {

    @Test
    fun calculateIMC_CorrectValues_ReturnsExpectedIMC() {
        val resultado = calculateIMC(70.0, 175)

        assertEquals(22.86, resultado, 0.01)
    }

    @Test
    fun calculateIMC_AnotherValidValues_ReturnsExpectedIMC() {
        val resultado = calculateIMC(80.0, 180)

        assertEquals(24.69, resultado, 0.01)
    }

    @Test
    fun calculateIMC_ZeroWeight_ThrowsException() {
        assertThrows(IllegalArgumentException::class.java) {
            calculateIMC(0.0, 175)
        }
    }
}