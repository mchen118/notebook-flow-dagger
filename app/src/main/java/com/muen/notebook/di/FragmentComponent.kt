package com.muen.notebook.di

import android.content.Context
import com.muen.notebook.view.EditFragment
import com.muen.notebook.view.ListFragment
import dagger.BindsInstance
import dagger.Component

@FragmentScope
@Component(modules = [DatabaseModule::class])
interface FragmentComponent{

    // tells dagger that ListFragment and EditFragment request (field) injection
    fun inject(listFragment: ListFragment)

    fun inject(editFragment: EditFragment)

    @Component.Builder
    interface Builder{

        // adds application context into dependency graph
        @BindsInstance
        fun applicationContext(context: Context): Builder

        // adds timeCreated into dependency graph
        @BindsInstance
        fun timeCreated(time: Long): Builder

        fun build(): FragmentComponent
    }
}