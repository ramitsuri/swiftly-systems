package com.ramitsuri.swiftly.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ramitsuri.swiftly.databinding.SpecialsItemBinding
import com.ramitsuri.swiftly.entities.SpecialsInfo

class SpecialsAdapter(
    private val list: MutableList<SpecialsInfo>
) :
    RecyclerView.Adapter<SpecialsAdapter.ViewHolder>() {

    var onItemClick: ((SpecialsInfo) -> Unit)? = null

    fun update(newList: List<SpecialsInfo>) {
        list.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            SpecialsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value: SpecialsInfo = list[position]
        holder.bind(value)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val binding: SpecialsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.container.setOnClickListener {
                onItemClick?.invoke(list[bindingAdapterPosition])
            }
        }

        fun bind(item: SpecialsInfo) {
            binding.apply {
                textDisplayName.text = item.displayName
                textOldPrice.text = item.oldPrice.toString()
                textNewPrice.text = item.newPrice.toString()
            }
        }
    }
}