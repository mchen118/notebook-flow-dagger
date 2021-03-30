package com.muen.notebook.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.muen.notebook.adatper.NotesAdapter
import com.muen.notebook.database.Note
import com.muen.notebook.databinding.FragmentListBinding
import com.muen.notebook.di.DaggerFragmentComponent
import com.muen.notebook.viewmodel.NotebookViewModel
import javax.inject.Inject


class ListFragment : Fragment() {

//    private val viewModel : NoteEditViewModel by lazy{
//        Log.d("ListFragment", "time created: ${args.timeCreated}")
//        ViewModelProvider(this,
//            NotebookViewModelProviderFactory(
//                NotebookRepository(
//                    (requireActivity().application as NotebookApplication).dao)))
//            .get(NoteListViewModel::class.java)
//    }
//
//    private val viewModel : NotesViewModel by viewModels {
//        NotebookViewModelProviderFactory(
//            NotebookRepository(
//                (requireActivity().application as NotebookApplication).dao))
//    }

    @Inject
    lateinit var viewModel: NotebookViewModel

    @Inject
    lateinit var adapter: NotesAdapter

    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val time = (viewHolder as NotesAdapter.NoteViewHolder).binding.note!!.timeCreated
            viewModel.deleteNote(time)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // performs field injection
        DaggerFragmentComponent.builder()
                .applicationContext(requireActivity().application)
                .timeCreated(-1L)
                .build()
                .inject(this)
        val binding = FragmentListBinding.inflate(layoutInflater).apply{
            rvNoteList.adapter = adapter
            ItemTouchHelper(simpleCallback).attachToRecyclerView(rvNoteList)
            btnAddNewNote.setOnClickListener{
                newNote()
            }
        }
        subscribeUi(adapter)
        return binding.root
    }


    private fun subscribeUi(adapter: NotesAdapter){
        viewModel.notes.observe(viewLifecycleOwner) { list ->
            Log.d("ListFragment", "list size = ${list.size}")
            adapter.submitList(list)
        }
    }

    fun newNote() {
        val time = System.currentTimeMillis()
        viewModel.insertNote(Note(time, ""))
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToEditFragment(time, true))
    }

    @VisibleForTesting
    fun deleteAllNotes(){
        viewModel.deleteAllNotes()
    }
}