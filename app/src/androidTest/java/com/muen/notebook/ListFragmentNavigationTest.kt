package com.muen.notebook

import androidx.navigation.testing.TestNavHostController
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.muen.notebook.view.ListFragment
import com.google.common.truth.Truth.assertThat
import com.muen.notebook.adatper.NotesAdapter
import com.muen.notebook.database.Note
import org.junit.Before


import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListFragmentNavigationTest {
    private val testText = "test"
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    private val listFragmentScenario = launchFragmentInContainer<ListFragment>()

    @Before
    fun setUpNavController(){
        runOnUiThread{
            navController.setGraph(R.navigation.nav_graph)
        }

        listFragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    fun setUpRecyclerView(){
        listFragmentScenario.onFragment{ fragment ->
            fragment.adapter.let{
                it.submitList(listOf(Note(123456789L, testText)))
                it.notifyDataSetChanged()
            }
        }
    }

    @Test
    fun When_NEW_NOTE_button_is_clicked_Then_navigate_from_ListFragment_to_EditFragment() {
        onView(withId(R.id.btn_add_new_note)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.edit_fragment)
    }

    @Test
    fun When_NEW_NOTE_button_is_clicked_Then_navArg_isNewNote_is_true() {
        onView(withId(R.id.btn_add_new_note)).perform(click())
        assertThat(navController.backStack.last().arguments?.get("isNewNote")).isEqualTo(true)
    }

    @Test
    fun When_recycler_view_item_is_clicked_Then_navArg_timeCreated_is_from_that_item_and_isNewNote_is_false() {
        setUpRecyclerView()
        onView(withId(R.id.rv_note_list)).perform(
            RecyclerViewActions.actionOnItem<NotesAdapter.NoteViewHolder>(
                hasDescendant(withText(testText)), click()
            )
        )
        assertThat(navController.backStack.last().arguments?.get("isNewNote")).isEqualTo(false)
        assertThat(navController.backStack.last().arguments?.get("timeCreated")).isEqualTo(123456789L)
    }
}