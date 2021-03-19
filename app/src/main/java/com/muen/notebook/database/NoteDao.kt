package com.muen.notebook.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("Select * From notes Where time_created = :id ")
    fun getNoteByTimeCreated(id : Long) : Flow<Note>

    @Query("Select * From notes Order By time_created desc")
    fun getNotes() : Flow<List<Note>>

    @Query("Delete From notes where time_created = :time")
    suspend fun deleteNoteByTimeCreated(time : Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Update
    suspend fun updateNote(note : Note)
}