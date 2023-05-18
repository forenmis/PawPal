package com.example.pawpal.screens.home.medicine.create_notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pawpal.databinding.FragmentNotificationCreateBinding
import com.example.pawpal.screens.home.HomeViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class CreateNotificationFragment : Fragment() {

    private val viewModel by viewModels<CreateNotificationViewModel>()
    private val viewModelHome by activityViewModels<HomeViewModel>()

    private lateinit var binding: FragmentNotificationCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            timeLD.observe(viewLifecycleOwner) { binding.etStartAtTime.setText(it) }
            dateLD.observe(viewLifecycleOwner) { binding.etStartAtDate.setText(it) }
            processLD.observe(viewLifecycleOwner) { isProgress ->
                binding.progressGroup.isVisible = isProgress
                if (isProgress == false) {
                    viewModelHome.refreshLD.postValue(true)
                    findNavController().popBackStack()
                }
            }
            completeFillLD.observe(viewLifecycleOwner) {
                binding.btSaveNotification.isEnabled = it
            }
        }
        with(binding) {
            switchIsPeriod.setOnCheckedChangeListener { _, isChecked ->
                tilPeriod.isVisible = isChecked
                viewModel.changeIsPeriod(isChecked)
            }
            etTitleNotification.doAfterTextChanged { viewModel.changeTitle(it.toString()) }
            etPeriod.doAfterTextChanged { viewModel.changePeriod(it.toString().toInt()) }
            etRemindAt.doAfterTextChanged { viewModel.changeRemind(it.toString().toInt()) }
            etStartAtTime.setOnClickListener { showTimePicker() }
            etStartAtDate.setOnClickListener { showDatePicker() }
            btSaveNotification.setOnClickListener {
                viewModel.saveNotification()
            }
        }
    }

    private fun showTimePicker() {
        val now = Calendar.getInstance()
        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(now.get(Calendar.HOUR_OF_DAY))
            .setMinute(now.get(Calendar.MINUTE))
            .setTitleText("Select time")
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .build()
            .also { dialog ->
                dialog.addOnPositiveButtonClickListener {
                    viewModel.changeTime(
                        dialog.hour, dialog.minute
                    )
                }
            }.show(parentFragmentManager, "tag_time")
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("Select date")
            .build()
            .also { datePicker ->
                datePicker.addOnPositiveButtonClickListener { viewModel.changeDate(it) }
            }.show(parentFragmentManager, "tag_date")
    }
}