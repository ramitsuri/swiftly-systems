package com.ramitsuri.swiftly.ui.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ramitsuri.swiftly.R
import com.ramitsuri.swiftly.databinding.SpecialsItemBinding
import com.ramitsuri.swiftly.entities.SpecialsInfo
import com.ramitsuri.swiftly.utils.CurrencyFormatter
import com.ramitsuri.swiftly.utils.DeviceSizeHelper

class SpecialsAdapter(
    private val list: MutableList<SpecialsInfo>,
    private val currencyFormatter: CurrencyFormatter,
    context: Context
) :
    RecyclerView.Adapter<SpecialsAdapter.ViewHolder>() {
    private val screenWidth = DeviceSizeHelper.getScreenWidth(context)
    private var canvasUnit = 1
    var onItemClick: ((SpecialsInfo) -> Unit)? = null

    fun update(newList: List<SpecialsInfo>, canvasUnit: Int) {
        list.apply {
            clear()
            addAll(newList)
        }
        if (canvasUnit == 0) {
            this.canvasUnit = 1
        } else {
            this.canvasUnit = canvasUnit
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

    fun getItemWidthSpan(position: Int): Int {
        return list[position].frameWidth
    }

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
                textOldPrice.text = currencyFormatter.format(item.oldPrice)
                textNewPrice.text = currencyFormatter.format(item.newPrice)
                image.load(item.imageUrl)
            }

            // Set height and width of item
            val widthScaleFactor: Double = item.frameWidth.toDouble() / canvasUnit.toDouble()
            val heightScaleFactor: Double = item.frameHeight.toDouble() / canvasUnit.toDouble()
            binding.container.apply {
                val params = layoutParams
                val width = (widthScaleFactor * screenWidth).toInt()
                params.width = width - paddingStart - paddingEnd

                val height = (heightScaleFactor * screenWidth).toInt()
                //params.height = height - paddingTop - paddingBottom
                layoutParams = params
            }

            // Set text sizes
            val priceTextSize: Float
            val nameTextSize: Float
            val nameWidthPercent: Float
            binding.apply {
                val resources = container.context.resources
                when {
                    widthScaleFactor < 0.25 -> {
                        priceTextSize = resources.getDimension(R.dimen.text_caption1)
                        nameTextSize = resources.getDimension(R.dimen.text_caption1)
                        nameWidthPercent = 1F
                    }

                    widthScaleFactor < 0.5 -> {
                        priceTextSize = resources.getDimension(R.dimen.text_body1)
                        nameTextSize = resources.getDimension(R.dimen.text_caption2)
                        nameWidthPercent = 1F
                    }
                    widthScaleFactor < 1 -> {
                        priceTextSize = resources.getDimension(R.dimen.text_body2)
                        nameTextSize = resources.getDimension(R.dimen.text_body1)
                        nameWidthPercent = 0.8F
                    }
                    else -> {
                        priceTextSize = resources.getDimension(R.dimen.text_body3)
                        nameTextSize = resources.getDimension(R.dimen.text_body2)
                        nameWidthPercent = 0.6F
                    }
                }
                textOldPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, priceTextSize)
                textNewPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, priceTextSize)
                textDisplayName.setTextSize(TypedValue.COMPLEX_UNIT_PX, nameTextSize)
                val constraints = ConstraintSet()
                constraints.clone(container)
                constraints.constrainPercentWidth(R.id.textDisplayName, nameWidthPercent)
                constraints.applyTo(container)
            }
        }
    }
}