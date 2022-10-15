package com.example.tictactoe

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun play_test_game_owin_row() {
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button0))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button3))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button1))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button4))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button2))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O WIN!")))
    }

    @Test
    fun play_test_game_owin_col() {
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button0))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button1))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button3))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button4))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button6))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O WIN!")))
    }

    @Test
    fun play_test_game_owin_diagonal() {
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button0))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button1))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button4))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button3))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button8))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O WIN!")))
    }

    @Test
    fun play_test_game_xwin_row() {
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button0))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button3))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button1))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button4))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button8))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button5))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X WIN!")))
    }

    @Test
    fun play_test_game_xwin_col() {
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button0))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button1))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button3))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button4))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button8))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button7))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X WIN!")))
    }

    @Test
    fun play_test_game_xwin_diagonal() {
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button0))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button2))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button1))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button4))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button8))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button6))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X WIN!")))
    }

    @Test
    fun play_test_game_draw() {
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button0))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button1))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button4))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button3))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button2))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button6))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button7))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button8))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button5))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("DRAW!")))
    }

    @Test
    fun play_test_game_restart() {
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.button0))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))

        onView(withId(R.id.button1))
            .perform(click())
            .check(matches(withText("X")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))

        onView(withId(R.id.restart))
            .perform(click())

        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER O TURN...")))
        onView(withId(R.id.button0))
            .check(matches(withText("")))
        onView(withId(R.id.button1))
            .check(matches(withText("")))
        onView(withId(R.id.button2))
            .check(matches(withText("")))
        onView(withId(R.id.button3))
            .check(matches(withText("")))
        onView(withId(R.id.button4))
            .check(matches(withText("")))
        onView(withId(R.id.button5))
            .check(matches(withText("")))
        onView(withId(R.id.button6))
            .check(matches(withText("")))
        onView(withId(R.id.button7))
            .check(matches(withText("")))
        onView(withId(R.id.button8))
            .check(matches(withText("")))

        onView(withId(R.id.button0))
            .perform(click())
            .check(matches(withText("O")))
        onView(withId(R.id.game_status))
            .check(matches(withText("PLAYER X TURN...")))
    }
}