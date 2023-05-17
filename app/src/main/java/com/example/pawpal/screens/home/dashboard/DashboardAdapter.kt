package com.example.pawpal.screens.home.dashboard

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawpal.R
import com.example.pawpal.databinding.ItemDashboardBinding
import com.example.pawpal.databinding.ItemDashboardSeeAllBinding
import com.example.pawpal.entity.Food
import com.example.pawpal.entity.Item
import com.example.pawpal.entity.Toy
import com.example.pawpal.utils.layoutInflater
import com.example.pawpal.utils.setImageRoundCorners

class DashboardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                VHItem(
                    ItemDashboardBinding.inflate(
                        parent.layoutInflater,
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_SEE_ALL -> {
                VHSeeAll(
                    ItemDashboardSeeAllBinding.inflate(
                        parent.layoutInflater,
                        parent,
                        false
                    )
                )
            }

            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is VHItem) holder.fillItem(item)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Item.FoodItem, is Item.ToyItem -> VIEW_TYPE_ITEM
            is Item.SeeAll -> VIEW_TYPE_SEE_ALL
        }
    }

    fun updateItems(newItems: List<Item>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class VHItem(private val binding: ItemDashboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun fillItem(item: Item) {
            when (item) {
                is Item.FoodItem -> fillFood(item.food)
                is Item.ToyItem -> fillToy(item.toy)
                else -> throw IllegalArgumentException()
            }
        }

        private fun fillFood(food: Food) {
            binding.tvTitle.text = food.name
            binding.tvPrice.text = binding.tvPrice.context.getString(R.string.hryvnya, food.price)
            binding.ivImage.setImageRoundCorners(food.image)
        }

        private fun fillToy(toy: Toy) {
            binding.tvTitle.text = toy.name
            binding.tvPrice.text = binding.tvPrice.context.getString(R.string.hryvnya, toy.price)
            binding.ivImage.setImageRoundCorners(toy.image)
        }
    }

    class VHSeeAll(binding: ItemDashboardSeeAllBinding) : RecyclerView.ViewHolder(binding.root)


    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_SEE_ALL = 2
    }
}