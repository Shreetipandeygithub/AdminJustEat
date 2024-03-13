package com.shreeti.adminjusteat

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.shreeti.adminjusteat.databinding.ActivityAddItemBinding
import com.shreeti.adminjusteat.model.AllMenu

class AddItemActivity : AppCompatActivity() {
    //food items details
    private lateinit var foodNames:String
    private lateinit var foodPrice:String
    private lateinit var foodDescription:String
    private lateinit var foodIngredient:String
    private var foodImageUri:Uri?=null
    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseDatabase


    private val binding:ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initialize firebase
        auth=FirebaseAuth.getInstance()
        //initialize firebase databse instance
        database=FirebaseDatabase.getInstance()


        binding.addItemBtn.setOnClickListener {
            //get data from fields
            foodNames=binding.editFoodName.text.toString().trim()
            foodPrice=binding.editFoodPrice.text.toString().trim()
            foodDescription=binding.descriptionTextView.text.toString().trim()
            foodIngredient=binding.ingredientTextView.text.toString().trim()

            //check if the entered value is not empty
            if(!(foodNames.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank())){
                uploadData()
                Toast.makeText(this,"Item Add Successfully",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Fill All the Details.",Toast.LENGTH_SHORT).show()

            }
        }

        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }


        binding.backButton.setOnClickListener {
            finish()
        }


    }

    private fun uploadData() {
        //get a reference to the menu node in the database
        val menuRef=database.getReference("menu")
        //generate unique key for the new menu
        val newItemKey=menuRef.push().key

        if (foodImageUri!=null){
            val storageRef=FirebaseStorage.getInstance().reference
            val imageRef=storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask=imageRef.putFile(foodImageUri!!)

            //upload
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl->
                    //create a new menu item
                    val newItem=AllMenu(
                        foodName=foodNames,
                        foodPrice=foodPrice,
                        foodDescription=foodDescription,
                        foodIngredient=foodIngredient,
                        foodImages = downloadUrl.toString(),
                    )
                    newItemKey?.let{
                        key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"Data Uploaded Successfully",Toast.LENGTH_SHORT).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this,"Failed to Upload Data",Toast.LENGTH_SHORT).show()
                            }
                    }
                }

            }
                .addOnFailureListener {
                    Toast.makeText(this,"Image Upload Failed",Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this,"Please select Image",Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        if(uri!=null){
            binding.imageSelected.setImageURI(uri)
            foodImageUri=uri
        }
    }
}