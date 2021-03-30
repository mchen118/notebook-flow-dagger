package com.muen.notebook

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.muen.notebook.database.Note
import com.muen.notebook.database.NoteDao
import com.muen.notebook.database.NotebookDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@SmallTest
@RunWith(AndroidJUnit4::class)
class NotebookDatabaseTest {
    private lateinit var db: NotebookDatabase
    private lateinit var noteDao: NoteDao

    private val note1 = Note(1L, "Hello")
    private val note2 = Note(2L, "World")

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NotebookDatabase::class.java).build()
        noteDao = db.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun NoteDao_getNotes(){
        runBlocking {
            noteDao.insertNote(note1)
            noteDao.insertNote(note2)
            val resultNoteList = noteDao.getNotes().first()
            assertThat(resultNoteList).hasSize(2)
        }
    }

    @Test
    fun NoteDao_getNoteByTimeCreated(){
        runBlocking {
            noteDao.insertNote(note1)
            noteDao.insertNote(note2)
            val resultNote = noteDao.getNoteByTimeCreated(1L).first()
            assertThat(resultNote.hasSameContentAs(note1)).isTrue()
        }
    }

    @Test
    fun noteDao_deleteNote(){
        runBlocking {
            noteDao.insertNote(note1)
            noteDao.insertNote(note2)
            noteDao.deleteNote(note1)
            val resultNote = noteDao.getNotes().first()
            assertThat(resultNote).hasSize(1)
            assertThat(resultNote[0].hasSameContentAs((note2))).isTrue()
        }
    }

    @Test
    fun NoteDao_deleteNoteByTimeCreated(){
        runBlocking {
            noteDao.insertNote(note1)
            noteDao.insertNote(note2)
            noteDao.deleteNoteByTimeCreated(3L)
            val resultNote = noteDao.getNotes().first()
            assertThat(resultNote).hasSize(2)
        }
    }

    @Test
    fun NoteDao_updateNote(){
        runBlocking {
            noteDao.insertNote(note1)
            noteDao.insertNote(note2)
            noteDao.updateNote(Note(1L, "updated"))
            val resultNote = noteDao.getNoteByTimeCreated(1L).first()
            assertThat(resultNote.noteContent).isEqualTo("updated")
        }
    }
}