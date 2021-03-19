package com.muen.notebook.adatper

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muen.notebook.database.Note
import com.muen.notebook.databinding.ListItemNoteBinding
import com.muen.notebook.view.ListFragmentDirections
import javax.inject.Inject

class NotesAdapter @Inject constructor() : ListAdapter<Note, RecyclerView.ViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ListItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = getItem(position)
        Log.d("NoteAdapter", "onBindViewHolder() called, and note = ${note.timeCreated}, ${note.noteContent}")
        (holder as NoteViewHolder).bind(note)
    }

    class NoteViewHolder(val binding: ListItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {
            binding.apply {
                note = item
                root.setOnClickListener{
                    it.findNavController().navigate(
                        ListFragmentDirections.actionListFragmentToEditFragment(item.timeCreated,
                                false))
                }
                executePendingBindings()
            }
        }
    }
}

private class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.timeCreated == newItem.timeCreated
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.noteContent == newItem.noteContent &&
                oldItem.timeCreated == newItem.timeCreated
    }
}