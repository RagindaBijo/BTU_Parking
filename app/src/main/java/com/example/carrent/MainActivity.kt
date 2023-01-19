package com.example.carrent

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.carrent.fragments.ProfileFragment
import com.example.carrent.fragments.RentalFragment
import com.example.carrent.fragments.TimeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        supportActionBar?.hide()
        bottomNavigationView = findViewById(R.id.bottomNavView)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)


        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {

            if (it.exists()) {

                val name = it.child("name").value
                val surName  = it.child("surName").value
                val personalID = it.child("personalID").value
                val phoneNumber  = it.child("phoneNumber").value


            }else {
                val intent=Intent(this,RegistrationInfo::class.java)
                startActivity(intent)
            }

        }.addOnFailureListener {
            Toast.makeText(this, "ხარვეზი!", Toast.LENGTH_SHORT).show()
        }


    }


}