package com.killkinto.zapplus.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.killkinto.zapplus.data.model.Property
import com.killkinto.zapplus.databinding.CardPropertyItemBinding

class PropertyListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Property, PropertyListAdapter.PropertyViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PropertyViewHolder {
        return PropertyViewHolder(
            CardPropertyItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val imovel = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(imovel)
        }
        holder.bind(imovel)
    }

    class PropertyViewHolder(private var binding: CardPropertyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(property: Property) {
            binding.property = property
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (property: Property) -> Unit) {
        fun onClick(property: Property) = clickListener(property)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Property>() {
        override fun areItemsTheSame(oldItem: Property, newItem: Property) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Property, newItem: Property) =
            oldItem.id == newItem.id

    }
}
