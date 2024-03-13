package com.shreeti.adminjusteat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreeti.adminjusteat.Adapter.PendingOrderAdapter
import com.shreeti.adminjusteat.databinding.ActivityPendingOrderBinding
import com.shreeti.adminjusteat.databinding.ItemsPendingOrdersBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPendingOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val customerName= arrayListOf("Shreeti","Babita","Rakesh","Shree","RatanRaj")
        val quantity= arrayListOf("5","8","4","5","9")
        val foodImages= arrayListOf(R.drawable.menu1,R.drawable.menu2,R.drawable.menu3,R.drawable.menu4,R.drawable.menu5)

        val adapter=PendingOrderAdapter(customerName,quantity, foodImages ,this)
        binding.pendingOrderRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.pendingOrderRecyclerView.adapter=adapter
    }
}