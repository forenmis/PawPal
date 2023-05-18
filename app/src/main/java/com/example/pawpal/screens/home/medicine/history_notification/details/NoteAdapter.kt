package com.example.pawpal.screens.home.medicine.history_notification.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpal.databinding.ItemNotesBinding
import com.example.pawpal.screens.home.medicine.entity.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.VH>() {

    private val itemNotes = mutableListOf<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return itemNotes.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val note = itemNotes[position]
        holder.feelItemNote(note)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItemNotes: List<Note>) {
        itemNotes.clear()
        itemNotes.addAll(newItemNotes)
        notifyDataSetChanged()
    }

    class VH(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun feelItemNote(note: Note) {
            binding.tvNoteTitle.text = note.title
        }
    }
}