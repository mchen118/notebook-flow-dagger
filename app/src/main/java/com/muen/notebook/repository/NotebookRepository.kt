package com.muen.notebook.repository

import androidx.annotation.VisibleForTesting
import com.muen.notebook.database.Note
import com.muen.notebook.database.NoteDao
import com.muen.notebook.di.FragmentScope
import javax.inject.Inject

// NotebookRepository acts as an intermediary between view model and data sources
@FragmentScope
class NotebookRepository @Inject constructor(private val dao : NoteDao) {

    fun getNotes() = dao.getNotes()

    suspend fun updateNote(note : Note) = dao.updateNote(note)

    suspend fun insertNote(note : Note) = dao.insertNote(note)

    suspend fun deleteNote(time : Long) = dao.deleteNoteByTimeCreated(time)

    @VisibleForTesting
    suspend fun deleteAllNotes() = dao.deleteAllNotes()
}