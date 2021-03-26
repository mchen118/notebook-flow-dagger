package com.muen.notebook

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.testing.TestNavHostController
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.google.common.truth.Truth.assertThat
import com.muen.notebook.view.EditFragment

import org.junit.Before


import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditFragmentNavigationTest {
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    private lateinit var editFragmentScenario: FragmentScenario<EditFragment>

    @Before
    fun setUp(){
        val bundle = Bundle().also{
            it.putLong("timeCreated", 123456789L)
            it.putBoolean("isNewNote", false)
        }
        runOnUiThread{
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.edit_fragment)
        }

        editFragmentScenario = launchFragmentInContainer<EditFragment>(bundle)
        editFragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun When_SAVE_button_is_clicked_Then_navigate_from_EditFragment_to_ListFragment() {
        onView(ViewMatchers.withId(R.id.btn_save_note)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.list_fragment)
    }

    @Test
    fun When_CANCEL_button_is_clicked_Then_navigate_from_EditFragment_to_ListFragment() {
        onView(ViewMatchers.withId(R.id.btn_cancel)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.list_fragment)
    }
}