package com.example.testeableapp

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.testeableapp.ui.Screens.TipCalculatorScreen
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test

class TipCalculatorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun roundUpCheckboxTest() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }
        // Ingresar monto
        composeTestRule.onNodeWithTag("billInput").performTextInput("105")

        // Activar redondeo
        composeTestRule.onNodeWithTag("roundCheckbox").performClick()

        // Validar propina redondeada: 105 * 0.15 = 15.75 → redondeado = 16.0
        composeTestRule
            .onNodeWithTag("tipAmount")
            .assertTextEquals("Propina: \$16.00")
    }

    @Test
    fun TipSliderTest() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithTag("billInput").performTextInput("100")

        // Mover slider a 50%
        composeTestRule.onNodeWithTag("tipSlider").performSemanticsAction(SemanticsActions.SetProgress) { it(50.0f) }


        // Puede variar según precisión del swipe → aceptable verificar existencia parcial
        composeTestRule
            .onNodeWithTag("tipAmount")
            .assertTextEquals("Propina: $50.00")
    }

    @Test
    fun uiElements_areVisible() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithTag("billInput").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tipSlider").assertIsDisplayed()
        composeTestRule.onNodeWithTag("roundCheckbox").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tipAmount").assertIsDisplayed()
        composeTestRule.onNodeWithTag("totalPerPerson").assertIsDisplayed()
    }

    // Pruebas de UI adicionales

    // Test para verificar que el monto por persona se calcula correctamente al aumentar el número de personas
    @Test
    fun increasePeopleCount() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithTag("billInput").performTextInput("100")

        // Validar total por persona inicial
        composeTestRule.onNodeWithTag("totalPerPerson")
            .assertTextContains("100.00")

        // Presionar botón "+" para aumentar número de personas
        composeTestRule.onNodeWithTag("AddPersonButton").performClick()

        // Validar que el total por persona disminuye al dividir entre más personas
        composeTestRule.onNodeWithTag("totalPerPerson")
            .assertTextEquals("50.00")
    }


    // Test para verificar que la propina se actualiza al seleccionar redondeo
    @Test
    fun roundUpCheckbox() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithTag("billInput").performTextInput("101")

        // Captura valor inicial sin redondeo (120 * 15% = 18)
        composeTestRule.onNodeWithTag("tipAmount")
            .assertTextEquals("Propina: \$15.15")

        // Activar redondeo
        composeTestRule.onNodeWithTag("roundCheckbox").performClick()

        // Ahora debe redondearse hacia arriba (18.0 → 18.0, pero útil si fuera 18.3)
        composeTestRule.onNodeWithTag("tipAmount")
            .assertTextEquals("Propina: \$16.00") // Asegura que la propina se actualizó
    }
}