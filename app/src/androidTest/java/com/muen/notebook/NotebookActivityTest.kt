package com.muen.notebook

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.muen.notebook.adatper.NotesAdapter
import com.muen.notebook.view.ListFragment
import com.muen.notebook.view.NotebookActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@LargeTest
@RunWith(AndroidJUnit4::class)
class NotebookActivityTest {
    private val testText1 = "testText1"
    private val testText2 = "testText2"

    @get:Rule
    var activityScenarioRule = activityScenarioRule<NotebookActivity>()

    @Before
    fun clearDb(){
        activityScenarioRule.scenario.onActivity {
            (it.supportFragmentManager.fragments.last().
            childFragmentManager.fragments.last() as ListFragment).deleteAllNotes()
        }
    }

    @Test
    fun When_SAVE_button_is_clicked_If_it_is_a_new_note_Then_a_new_note_is_saved(){
        onView(withId(R.id.btn_add_new_note)).perform(click())
        onView(withId(R.id.note_edit_text)).perform(replaceText(testText1))
        onView(withId(R.id.btn_save_note)).perform(click())
        onView(withId(R.id.rv_note_list)).check(matches(
                hasDescendant(withText(testText1))
        ))
    }

    @Test
    fun When_SAVE_button_is_clicked_If_it_is_an_existing_note_Then_changes_are_saved_to_existing_note(){
        onView(withId(R.id.btn_add_new_note)).perform(click())
        onView(withId(R.id.note_edit_text)).perform(replaceText(testText1))
        onView(withId(R.id.btn_save_note)).perform(click())
        onView(withId(R.id.rv_note_list)).perform(
                RecyclerViewActions.actionOnItem<NotesAdapter.NoteViewHolder>(
                        hasDescendant(withText(testText1)), click()
                )
        )
        onView(withId(R.id.note_edit_text)).perform(replaceText(testText2))
        onView(withId(R.id.btn_save_note)).perform(click())
        onView(withId(R.id.rv_note_list)).check(matches(
                not(hasDescendant(withText(testText1)))
        ))
        onView(withId(R.id.rv_note_list)).check(matches(
                hasDescendant(withText(testText2))
        ))
    }

    @Test
    fun When_CANCEL_button_is_clicked_Then_no_changes_are_made(){
        onView(withId(R.id.btn_add_new_note)).perform(click())
        onView(withId(R.id.note_edit_text)).perform(replaceText(testText1))
        onView(withId(R.id.btn_save_note)).perform(click())
        onView(withId(R.id.rv_note_list)).perform(
                RecyclerViewActions.actionOnItem<NotesAdapter.NoteViewHolder>(
                        hasDescendant(withText(testText1)), click()
                )
        )
        onView(withId(R.id.note_edit_text)).perform(replaceText(testText2))
        onView(withId(R.id.btn_cancel)).perform(click())
        onView(withId(R.id.rv_note_list)).check(matches(
                hasDescendant(withText(testText1))
        ))
        onView(withId(R.id.rv_note_list)).check(matches(
                not(hasDescendant(withText(testText2)))
        ))
    }

    @Test
    fun When_recycler_view_item_is_swiped_left_Then_that_item_is_deleted(){
        onView(withId(R.id.btn_add_new_note)).perform(click())
        onView(withId(R.id.note_edit_text)).perform(replaceText(testText1))
        onView(withId(R.id.btn_save_note)).perform(click())
        onView(withId(R.id.rv_note_list)).perform(
                RecyclerViewActions.actionOnItem<NotesAdapter.NoteViewHolder>(
                        hasDescendant(withText(testText1)), swipeLeft()
                )
        )
        // it takes time for the item's view to be destroyed
        sleep(250)
        onView(withId(R.id.rv_note_list)).check(matches(
                not(hasDescendant(withText(testText1)))
        ))
    }
}