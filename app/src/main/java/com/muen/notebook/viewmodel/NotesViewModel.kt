package com.muen.notebook.viewmodel

import androidx.lifecycle.*
import com.muen.notebook.database.Note
import com.muen.notebook.di.FragmentScope
import com.muen.notebook.repository.NotebookRepository
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class NotesViewModel @Inject constructor(private val repo: NotebookRepository,
                                         private val time: Long) : ViewModel(){

    val note: LiveData<Note> = repo.getNotes().transform{
        for (note in it) {
            if (note.timeCreated == time) emit(note)
        }
    }.asLiveData()

    val notes: LiveData<List<Note>> = repo.getNotes().asLiveData()

    fun updateNote(note: Note) {
        viewModelScope.launch{
            repo.updateNote(note)
        }
    }

    fun insertNote(note: Note){
        viewModelScope.launch{
            repo.insertNote(note)
        }
    }

    fun deleteNote(time: Long) {
        viewModelScope.launch{
            repo.deleteNote(time)
        }
    }
}