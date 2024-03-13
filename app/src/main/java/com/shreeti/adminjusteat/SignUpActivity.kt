package com.shreeti.adminjusteat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shreeti.adminjusteat.databinding.ActivitySignUpBinding
import com.shreeti.adminjusteat.model.UserModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var userName:String
    private lateinit var nameOfRestaurants:String
    private lateinit var database:DatabaseReference

    private val binding:ActivitySignUpBinding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase auth
        auth=Firebase.auth
        //initialize firebase database

        database= Firebase.database.reference



        val locationsList=arrayOf("Jaipur","Madras","Bangalore","Mumbai","Delhi")
        val adapters=ArrayAdapter(this,android.R.layout.simple_list_item_1,locationsList)
        val autoCompleteTextView=binding.listOfLocation
        autoCompleteTextView.setAdapter(adapters)

        //////creating new account binding
        binding.createAccountBtn.setOnClickListener{
            //get text from edit text
            userName=binding.nameOfOwner.text.toString().trim()
            nameOfRestaurants=binding.nameOfRestaurant.text.toString().trim()
            email=binding.emailAddress.text.toString().trim()
            password=binding.password.text.toString().trim()
            if (userName.isBlank()|| nameOfRestaurants.isBlank()|| email.isBlank()|| password.isBlank()){
                Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //////already have account binding
        binding.alreadyHaveBtn.setOnClickListener{
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun createAccount(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task->
            //if account get created successfully
            if(task.isSuccessful){
                Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            //if account failed to created
            }else{
                Toast.makeText(this,"Account Creation Failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","CreateAccount:", task.exception)
            }
        }

    }

    private fun saveUserData() {
        userName=binding.nameOfOwner.text.toString().trim()
        nameOfRestaurants=binding.nameOfRestaurant.text.toString().trim()
        email=binding.emailAddress.text.toString().trim()
        password=binding.password.text.toString().trim()

        val user=UserModel(userName,nameOfRestaurants,email,password)
        val userId=FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}