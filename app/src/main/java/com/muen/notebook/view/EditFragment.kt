package com.muen.notebook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.muen.notebook.database.Note
import com.muen.notebook.databinding.FragmentEditBinding
import com.muen.notebook.di.DaggerFragmentComponent
import com.muen.notebook.viewmodel.NotesViewModel
import javax.inject.Inject

class EditFragment : Fragment() {

    private val args : EditFragmentArgs by navArgs()

    private val binding by lazy{
        FragmentEditBinding.inflate(layoutInflater).apply {
            btnSaveNote.setOnClickListener {
                saveNote()
            }
            btnCancel.setOnClickListener {
                cancel()
            }
            this.lifecycleOwner = this@EditFragment
        }
    }

//    private val viewModel : NoteEditViewModel by lazy{
//        Log.d("EditFragment", "time created: ${args.timeCreated}")
//        ViewModelProvider(this,
//            NotebookViewModelProviderFactory(
//                NotebookRepository(
//                    (requireActivity().application as NotebookApplication).dao), args.timeCreated))
//            .get(NoteEditViewModel::class.java)
//    }
//
//    private val viewModel: NotesViewModel by viewModels {
//        NotebookViewModelProviderFactory(
//                NotebookRepository(
//                        (requireActivity().application as NotebookApplication).dao), args.timeCreated)
//    }

    @Inject
    lateinit var viewModel: NotesViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // performs field injection
        DaggerFragmentComponent.builder()
                .applicationContext(requireActivity().application)
                .timeCreated(args.timeCreated)
                .build()
                .inject(this)
        binding.viewModel = viewModel
        return binding.root
    }

    private fun saveNote() {
        viewModel.updateNote(Note(args.timeCreated, binding.noteEditText.text.toString()))
        findNavController().navigate(
            EditFragmentDirections.actionEditFragmentToListFragment())
    }

    private fun cancel() {
        when(args.isNewNote) {
            true -> viewModel.deleteNote(args.timeCreated)
            false -> {}
        }

        findNavController().navigate(
            EditFragmentDirections.actionEditFragmentToListFragment())
    }
}