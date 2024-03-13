package com.shreeti.adminjusteat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shreeti.adminjusteat.Adapter.MenuItemAdapter
import com.shreeti.adminjusteat.databinding.ActivityAllItemBinding
import com.shreeti.adminjusteat.model.AllMenu

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseRef:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private var menuItem:ArrayList<AllMenu> = ArrayList()
    private val binding:ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseRef=FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

//        val menuFoodName= listOf("Pasta","Kabaab Paratha","Fruit Custurd","Ice Cream","Avocado Salad")
//        val menuItemPrices= listOf("₹250","₹120","₹180","₹100","₹200")
//        val menuFoodImage= listOf(R.drawable.menu5,R.drawable.menu6,R.drawable.menu7,R.drawable.menu3,R.drawable.menu2)
//        val adapter=MenuItemAdapter(ArrayList(menuFoodName),ArrayList(menuItemPrices),ArrayList(menuFoodImage),Context)
//        binding.menuRecyclerView.layoutManager=LinearLayoutManager(this)
//        binding.menuRecyclerView.adapter=adapter

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    //to retrieve data from the database
    private fun retrieveMenuItem() {
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear existing data before populating
                menuItem.clear()

                for (foodSnapshot in snapshot.children){
                    val menuItems=foodSnapshot.getValue(AllMenu::class.java)
                    menuItems?.let {
                        menuItem.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error: ${error.message}")
            }
        })

    }
    private fun setAdapter() {
        val adapter=MenuItemAdapter(this@AllItemActivity,menuItem,databaseRef)
        binding.menuRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.menuRecyclerView.adapter=adapter
    }

}