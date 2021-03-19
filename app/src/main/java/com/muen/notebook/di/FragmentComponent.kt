package com.muen.notebook.di

import android.content.Context
import com.muen.notebook.view.EditFragment
import com.muen.notebook.view.ListFragment
import dagger.BindsInstance
import dagger.Component

@FragmentScope
@Component(modules = [DatabaseModule::class])
interface FragmentComponent{

    fun inject(listFragment: ListFragment)

    fun inject(editFragment: EditFragment)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun applicationContext(context: Context): Builder

        @BindsInstance
        fun timeCreated(time: Long): Builder

        fun build(): FragmentComponent
    }
}