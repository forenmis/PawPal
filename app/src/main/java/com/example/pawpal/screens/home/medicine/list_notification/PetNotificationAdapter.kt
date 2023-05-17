package com.example.pawpal.screens.home.medicine.list_notification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpal.databinding.ItemNotificationBinding
import com.example.pawpal.databinding.ItemSortNotificationBinding
import com.example.pawpal.screens.home.medicine.list_notification.entity.ItemNotifications
import com.example.pawpal.utils.fullString

class PetNotificationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val itemNotifications = mutableListOf<ItemNotifications>()
    var callbackNotification: ((Long) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return when (itemNotifications[position]) {
            is ItemNotifications.NotificationItem -> VIEW_TYPE_NOTIFICATION
            is ItemNotifications.GroupItem -> VIEW_TYPE_GROUP
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NOTIFICATION -> VHNotification(
                ItemNotificationBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_GROUP -> VHGroup(
                ItemSortNotificationBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = itemNotifications.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VHNotification -> {
                val notification = itemNotifications[position] as ItemNotifications.NotificationItem
                holder.feelNotification(notification)
                holder.viewBinding.cvItem.setOnClickListener {
                    callbackNotification!!.invoke(notification.petNotification.id)
                }
            }

            is VHGroup -> holder.feelGroupItem(itemNotifications[position] as ItemNotifications.GroupItem)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItemNotifications: List<ItemNotifications>) {
        itemNotifications.clear()
        itemNotifications.addAll(newItemNotifications)
        notifyDataSetChanged()
    }

    class VHNotification(binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var viewBinding = binding
        fun feelNotification(itemNotifications: ItemNotifications.NotificationItem) {
            viewBinding.tvTitleNotification.text = itemNotifications.petNotification.title
            viewBinding.tvDateNotification.text =
                itemNotifications.petNotification.date.fullString()
        }
    }

    class VHGroup(private val binding: ItemSortNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun feelGroupItem(groupItem: ItemNotifications.GroupItem) {
            binding.tvGroupTitle.text = groupItem.title
        }
    }

    companion object {
        private const val VIEW_TYPE_NOTIFICATION = 1
        private const val VIEW_TYPE_GROUP = 2
    }
}