package com.example.pawpal.screens.home.medicine.history_notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pawpal.databinding.FragmentNotificationHistoryBinding
import com.example.pawpal.screens.home.HomeViewModel
import com.example.pawpal.screens.home.medicine.list_notification.PetNotificationAdapter

class HistoryNotificationFragment : Fragment() {

    private val viewModelHome by activityViewModels<HomeViewModel>()
    private val viewModel by viewModels<HistoryNotificationViewModel>()

    private lateinit var binding: FragmentNotificationHistoryBinding
    private lateinit var adapter: PetNotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PetNotificationAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.callbackNotification = { viewModelHome.idNotificationLD.postValue(it) }
        with(binding) {
            rvNotifications.layoutManager = LinearLayoutManager(requireContext())
            rvNotifications.adapter = adapter
        }
        viewModel.listItemsLD.observe(viewLifecycleOwner) { adapter.updateItems(it) }
    }
}