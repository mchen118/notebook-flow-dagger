package com.muen.notebook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class Note (@PrimaryKey @ColumnInfo(name = "time_created") val timeCreated : Long,
            @ColumnInfo(name = "content") var noteContent : String){

    fun changeNoteContent(newContent: String): Note {
        noteContent = newContent
        return this
    }

    fun hasSameContentAs(note: Note): Boolean{
        return this.noteContent == note.noteContent && this.timeCreated == note.timeCreated
    }
}
