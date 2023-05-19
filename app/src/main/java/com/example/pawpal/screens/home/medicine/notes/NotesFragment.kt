package com.example.pawpal.screens.home.medicine.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawpal.databinding.FragmentNotificationNotesBinding
import com.example.pawpal.screens.home.HomeViewModel
import com.example.pawpal.screens.home.medicine.history_notification.details.NoteAdapter

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotificationNotesBinding
    private lateinit var notesAdapter: NoteAdapter

    private val viewModel by viewModels<NotesViewModel>()
    private val viewModelHome by activityViewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notesAdapter = NoteAdapter()
        notesAdapter.callbackNoteClick = { viewModelHome.idNotificationLD.postValue(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.rvNotesList) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notesAdapter
        }
        viewModel.noteListLD.observe(viewLifecycleOwner) { notesAdapter.updateItems(it) }
    }
}