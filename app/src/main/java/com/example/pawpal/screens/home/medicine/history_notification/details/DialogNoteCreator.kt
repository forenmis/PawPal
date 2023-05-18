package com.example.pawpal.screens.home.medicine.history_notification.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.pawpal.databinding.DialogNoteCreateBinding

class DialogNoteCreator : DialogFragment() {

    private lateinit var binding: DialogNoteCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogNoteCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btSaveNote.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_TITLE_NOTE, binding.et.text.toString())
            setFragmentResult(REQUEST_KEY_TO_DIALOG, bundle)
            dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        const val REQUEST_KEY_TO_DIALOG = "requestKeyToDialog"
        const val BUNDLE_KEY_TITLE_NOTE = "bundleKeyTitleNote"
    }
}