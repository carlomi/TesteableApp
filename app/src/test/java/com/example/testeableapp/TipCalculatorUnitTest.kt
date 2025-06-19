package com.example.testeableapp

import com.example.testeableapp.ui.Screens.calculateTip
import org.junit.Test
import kotlin.test.assertEquals

class TipCalculatorUnitTest {

    // Test para verificar el cálculo de propina con redondeo
    @Test
    fun calculateTip_37percent_withRoundUp() {
        val result = calculateTip(amount = 101.0, tipPercent = 37, roundUp = true)
        assertEquals(38.0, result)
    }

    // Test para verificar el cálculo de propina con valores negativos
    @Test
    fun calculateTip_negativeAmount() {
        val result = calculateTip( amount= -100.0, 20, false)
        assertEquals(0.0, result)
    }

    // Test para verificar que el calculo de monto por persona es correcto
    @Test
    fun totalPerPerson_test() {
        val bill = 100.0
        val tipPercent = 20
        val roundUp = false
        val numberOfPeople = 4

        val tip = calculateTip(bill, tipPercent, roundUp)
        val totalPerPerson = if (numberOfPeople > 0) (bill + tip) / numberOfPeople else 0.0

        assertEquals(30.0, totalPerPerson)
    }

    //Tests Unitarios Adicionales

    // Test para verificar el cálculo de propina sin redondeo
    @Test
    fun calculateTip_noRoundUp() {
        val result = calculateTip(amount = 125.0, tipPercent = 15, roundUp = false)
        assertEquals(18.75, result)
    }

    // Test para verificar el cálculo de propina de 0% es cero
    @Test
    fun calculateTip_zeroPercent() {
        val result = calculateTip(amount = 100.0, tipPercent = 0, roundUp = false)
        assertEquals(0.0, result)
    }

}
