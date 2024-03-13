package com.shreeti.adminjusteat.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.shreeti.adminjusteat.databinding.ItemsPendingOrdersBinding

class PendingOrderAdapter(
    private val CustomerName:ArrayList<String>,
    private val Quantity:ArrayList<String>,
    private val foodImages:ArrayList<Int>,
    private val context:Context
):RecyclerView.Adapter<PendingOrderAdapter.PendingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingViewHolder {
        val binding=ItemsPendingOrdersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return CustomerName.size
    }

    override fun onBindViewHolder(holder: PendingViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class PendingViewHolder(private val binding: ItemsPendingOrdersBinding):RecyclerView.ViewHolder(binding.root) {
        private var isAccepted=false
        fun bind(position: Int) {
            binding.apply {
                customerName.text=CustomerName[position]
                quantity.text=Quantity[position]
                orderFoodItemImageView.setImageResource(foodImages[position])

                orderAccept.apply {
                    if (isAccepted){
                        text="Accept"
                    }else{
                        text="Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted){
                            text="Dispatch"
                            isAccepted=true
                            showToast("Order is Accepted")
                        }else{
                            CustomerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order is Dispatch")
                        }
                    }
                }
            }
        }
        private fun showToast(message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }

    }
}