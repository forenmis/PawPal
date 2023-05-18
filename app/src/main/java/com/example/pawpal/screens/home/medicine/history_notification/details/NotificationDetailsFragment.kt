package com.example.pawpal.screens.home.medicine.history_notification.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawpal.databinding.FhagmentNotificationDetailsBinding
import com.example.pawpal.screens.home.medicine.entity.PetNotification
import com.example.pawpal.utils.getDateInStr
import com.example.pawpal.utils.getTimeInStr

class NotificationDetailsFragment : Fragment() {
    private val viewModel by viewModels<NotificationDetailsViewModel>()

    private lateinit var binding: FhagmentNotificationDetailsBinding
    private lateinit var adapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = NotificationDetailsFragmentArgs.fromBundle(requireArguments())
        viewModel.loadNotificationById(args.id)
        adapter = NoteAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FhagmentNotificationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = adapter
        viewModel.notificationDetailsLD.observe(viewLifecycleOwner) { fillDetails(it) }
        viewModel.notesLD.observe(viewLifecycleOwner) { adapter.updateItems(it) }
        binding.ivBack.setOnClickListener { findNavController().popBackStack() }
        binding.btCreateNotes.setOnClickListener {
            setFragmentResultListener(DialogNoteCreator.REQUEST_KEY_TO_DIALOG) { _, bundle ->
                val title =
                    bundle.getString(DialogNoteCreator.BUNDLE_KEY_TITLE_NOTE) ?: "empty_note"
                viewModel.saveNote(title)

            }
            DialogNoteCreator().show(parentFragmentManager, DialogNoteCreator::class.simpleName)
        }

    }

    private fun fillDetails(petNotification: PetNotification) {
        with(binding) {
            etTitleNotification.setText(petNotification.title)
            etStartAtDate.setText(petNotification.date.getDateInStr())
            etStartAtTime.setText(petNotification.date.getTimeInStr())
            switchIsPeriod.isChecked = petNotification.isPeriod
            etRemindAt.setText(petNotification.remindIn.toString())
            if (petNotification.isPeriod) {
                etPeriod.setText(petNotification.periodDays.toString())
            }
        }
    }
}