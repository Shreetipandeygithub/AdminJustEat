package com.shreeti.adminjusteat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shreeti.adminjusteat.databinding.ActivityLoginBinding
import com.shreeti.adminjusteat.model.UserModel

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email:String
    private lateinit var password:String
    private var userName:String?=null
    private var nameOfRestaurants:String?=null
    private lateinit var database: DatabaseReference

    //google sign in.....extending variable with google
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOptions=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.app_name)).requestEmail().build()

        auth=Firebase.auth

        database=Firebase.database.reference
        googleSignInClient=GoogleSignIn.getClient(this,googleSignInOptions)



        binding.Loginbtn.setOnClickListener{
            email=binding.emailAddress.toString().trim()
            password=binding.passwordTxt.toString().trim()
            if (email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please fill details",Toast.LENGTH_SHORT).show()
            }else{
                createUserAccount(email,password)
            }
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
//            finish()
        }


        binding.googleButton.setOnClickListener {
            val signIntent=googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }


        binding.notHaveAccountBtn.setOnClickListener{
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
            //if user is already sign in
            if (task.isSuccessful){
                val user=auth.currentUser
                Toast.makeText(this,"Login Successfully",Toast.LENGTH_SHORT).show()
                updateUI(user)
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {  task->
                    if (task.isSuccessful){
                        val user=auth.currentUser
                        Toast.makeText(this,"Create User & Login Successfully",Toast.LENGTH_SHORT).show()
                        saveUserData()
                        updateUI(user)
                    }else{
                        Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
                        Log.d("Account","createUserAccount: Authentication Failure", task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        email=binding.emailAddress.toString().trim()
        password=binding.passwordTxt.toString().trim()

        //user's email and password will automatically get saved in database
        val user=UserModel(userName,nameOfRestaurants,email,password)
        val userid=FirebaseAuth.getInstance().currentUser?.uid
        userid?.let {
            database.child("user").child(userid).setValue(user)
        }
    }

    private val launcher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        if (result.resultCode== Activity.RESULT_OK){
            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (task.isSuccessful){
                val account:GoogleSignInAccount=task.result
                val credential=GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener { authTask->
                    if (authTask.isSuccessful){

                        //successfully sign in with google
                        Toast.makeText(this,"Successfully sign in with Google",Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this,MainActivity::class.java))

                    }else{
                        Toast.makeText(this,"Google sign in Failed",Toast.LENGTH_SHORT).show()

                    }
                }
            }else{
                Toast.makeText(this,"Google sign in Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
    //check if user is already logged in
    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if (currentUser!=null){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}