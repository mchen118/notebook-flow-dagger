package com.muen.notebook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotebookDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

    companion object {
        @Volatile private var instance: NotebookDatabase? = null

        fun getInstance(context: Context): NotebookDatabase {
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also {instance = it}
            }
        }

        private fun buildDatabase(context: Context) : NotebookDatabase {
            return Room.databaseBuilder(context.applicationContext, NotebookDatabase::class.java, "database").build()
        }
    }
}