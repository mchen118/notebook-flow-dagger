package com.muen.notebook.di

import android.content.Context
import com.muen.notebook.database.NoteDao
import com.muen.notebook.database.NotebookDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideAppDatabase(context: Context): NotebookDatabase {
        return NotebookDatabase.getInstance(context)
    }

    @Provides
    fun provideNoteDao(db: NotebookDatabase): NoteDao {
        return db.noteDao()
    }
}