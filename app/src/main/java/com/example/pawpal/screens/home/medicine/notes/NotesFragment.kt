package com.example.pawpal.screens.home.medicine.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pawpal.databinding.FragmentNotificationNotesBinding

class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotificationNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationNotesBinding.inflate(inflater, container, false)
        return binding.root
    }
}