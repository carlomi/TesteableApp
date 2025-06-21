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


        // En este caso usamos 50% como ejemplo
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
            .assertTextEquals("Total por persona: $115.00")

        // Presionar botón "+" para aumentar número de personas
        composeTestRule.onNodeWithTag("AddPersonButton").performClick()

        // Validar que el total por persona disminuye al dividir entre más personas
        composeTestRule.onNodeWithTag("totalPerPerson")
            .assertTextEquals("Total por persona: $57.50")
    }

    // Test para asegurarnos que cuando no hay datos en el campo de monto, la propina y total por persona son $0.00
    @Test
    fun emptyBill() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Verificamos que la propina sea $0.00
        composeTestRule.onNodeWithTag("tipAmount")
            .assertTextEquals("Propina: \$0.00")

        // También el total por persona debe ser $0.00
        composeTestRule.onNodeWithTag("totalPerPerson")
            .assertTextEquals("Total por persona: \$0.00")
    }
}