package com.shreeti.adminjusteat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shreeti.adminjusteat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addMenu.setOnClickListener {
            val intent=Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }

        binding.allItemMenuBtn.setOnClickListener {
            val intent=Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.orderDispatched.setOnClickListener {
            val intent=Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }

        binding.profileBtn.setOnClickListener {
            val intent=Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }

        binding.createNewUserBtn.setOnClickListener {
            val intent=Intent(this,CreateUsersActivity::class.java)
            startActivity(intent)
        }
        binding.pendingOrder.setOnClickListener {
            val intent=Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }
        binding.imageView3.setOnClickListener {
            val intent=Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }
    }
}