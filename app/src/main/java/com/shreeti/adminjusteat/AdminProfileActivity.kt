package com.shreeti.adminjusteat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shreeti.adminjusteat.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding:ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        var isEnable=false

        binding.clickHere.setOnClickListener {
            isEnable=!isEnable

            binding.adminName.isEnabled=isEnable
            binding.adminAddress.isEnabled=isEnable
            binding.adminEmail.isEnabled=isEnable
            binding.adminPassword.isEnabled=isEnable
            binding.adminPhone.isEnabled=isEnable

            if (isEnable){
                binding.adminName.requestFocus()
            }
        }
    }
}