package com.example.pawpal.screens.home.medicine.history_notification.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pawpal.databinding.FhagmentNotificationDetailsBinding
import com.example.pawpal.screens.home.medicine.entity.PetNotification
import com.example.pawpal.utils.getDateInStr
import com.example.pawpal.utils.getTimeInStr

class NotificationDetailsFragment : Fragment() {
    private val viewModel by viewModels<NotificationDetailsViewModel>()


    private lateinit var binding: FhagmentNotificationDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = NotificationDetailsFragmentArgs.fromBundle(requireArguments())
        viewModel.loadNotificationById(args.id)
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
        viewModel.notificationDetailsLD.observe(viewLifecycleOwner) { fillDetails(it) }
        binding.ivBack.setOnClickListener { findNavController().popBackStack() }
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