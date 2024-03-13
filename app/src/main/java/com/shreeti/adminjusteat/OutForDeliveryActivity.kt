package com.shreeti.adminjusteat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreeti.adminjusteat.Adapter.DeliveryAdapter
import com.shreeti.adminjusteat.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding:ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val customerName= arrayListOf("Shreeti","Babita","Rakesh","Shree","RatanRaj")
        val moneyStatus= arrayListOf("received","not received","pending","not received","pending")
        val adapter=DeliveryAdapter(customerName,moneyStatus)
        binding.outForDeliveryRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.outForDeliveryRecyclerView.adapter=adapter
    }
}