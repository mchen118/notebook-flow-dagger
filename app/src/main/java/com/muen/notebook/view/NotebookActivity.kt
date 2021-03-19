package com.muen.notebook.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.muen.notebook.databinding.ActivityNotebookBinding


class NotebookActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNotebookBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}