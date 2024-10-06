package com.example.firebaseauthexample

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.firebaseauthexample.screens.Authentication
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val rule = createComposeRule()
    //val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun authTest() {
        rule.setContent {
            Authentication()
        }
        rule.onNodeWithText("Email").performTextClearance()
        rule.onNodeWithText("Email").performTextInput("anbo@secret12.dk")

        rule.onNodeWithText("Password").performTextClearance()
        rule.onNodeWithText("Password").performTextInput("sludder")
        rule.onNodeWithText("Sign in").performClick()

        Thread.sleep(2000) // don't do this
        rule.onNodeWithText("incorrect", substring = true).assertExists()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.firebaseauthexample", appContext.packageName)
    }
}