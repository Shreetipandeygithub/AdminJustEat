package com.shreeti.adminjusteat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shreeti.adminjusteat.databinding.ActivityCreateUsersBinding

class CreateUsersActivity : AppCompatActivity() {
    private val binding:ActivityCreateUsersBinding by lazy {
        ActivityCreateUsersBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}