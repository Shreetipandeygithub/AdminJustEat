package com.shreeti.adminjusteat.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.shreeti.adminjusteat.databinding.DeliveryItemBinding

class DeliveryAdapter(
    private val customerName:ArrayList<String>,
    private val MoneyStatus:ArrayList<String>

    ):RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding=DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return customerName.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding):RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(position: Int) {
            binding.apply {
                customerNameTxt.text=customerName[position]
                moneyStatus.text=MoneyStatus[position]

                val colorMap= mapOf(
                    "received" to Color.GREEN,"not received" to Color.RED,"pending" to Color.GRAY
                )
                moneyStatus.setTextColor(colorMap[MoneyStatus[position]]?:Color.BLACK)
                statusColor.backgroundTintList= ColorStateList.valueOf(colorMap[MoneyStatus[position]]?:Color.BLACK)
            }
        }

    }

}