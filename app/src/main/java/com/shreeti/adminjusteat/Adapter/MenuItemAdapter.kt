package com.shreeti.adminjusteat.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.shreeti.adminjusteat.databinding.ItemsItemDetailsBinding
import com.shreeti.adminjusteat.model.AllMenu

class MenuItemAdapter(
//    private val menuItemName:ArrayList<String>,
//    private val menuItemPrice:ArrayList<String>,
//    private val menuItemImage:ArrayList<Int>

    private val context: Context,
    private val menuList:ArrayList<AllMenu>,
    private val databaseReference: DatabaseReference

):RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {


    private val itemQuantity=IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding=ItemsItemDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class AddItemViewHolder(private val binding:ItemsItemDetailsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {

                val menuItem=menuList[position]
                val uriString=menuItem.foodImages
                val uri=Uri.parse(uriString)
                val quantities=itemQuantity[position]

//                menuItemNameTxt.text=menuItemName[position]
                menuItemNameTxt.text=menuItem.foodName
//                menuTotalCost.text=menuItemPrice[position]
                menuTotalCost.text=menuItem.foodPrice
//                menuImageView.setImageResource(menuItemImage[position])
                Glide.with(context).load(uri).into(menuImageView)

                quantity.text=quantities.toString()
                minusBtn.setOnClickListener{
                    decreaseQuantity(position)
                }

                addBtn.setOnClickListener {
                    increaseQuantity(position)
                }
                deleteBtn.setOnClickListener {
                    deleteItem(position)
                }
            }
        }

        private fun deleteItem(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)
        }

        private fun increaseQuantity(position: Int) {
            if(itemQuantity[position]<10){
                itemQuantity[position]++
                binding.quantity.text=itemQuantity[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if(itemQuantity[position]>1){
                itemQuantity[position]--
                binding.quantity.text=itemQuantity[position].toString()
            }
        }

    }
}