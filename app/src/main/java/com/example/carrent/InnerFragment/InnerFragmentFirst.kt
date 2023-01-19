package com.example.carrent.InnerFragment


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.carrent.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class InnerFragmentFirst : Fragment(R.layout.fragment_inner_first) {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")

    private lateinit var displayName: TextView
    private lateinit var displaySurName: TextView
    private lateinit var displayID: TextView
    private lateinit var displayPhone: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        displayName = view.findViewById(R.id.name)
        displaySurName = view.findViewById(R.id.surName)
        displayID = view.findViewById(R.id.id)
        displayPhone = view.findViewById(R.id.phoneNumber)


        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {

            if (it.exists()) {

                val name = it.child("name").value
                val surName  = it.child("surName").value
                val personalID = it.child("personalID").value
                val phoneNumber  = it.child("phoneNumber").value

                displayName.text="სახელი: "+name.toString()
                displaySurName.text="გვარი: "+surName.toString()
                displayID.text="ID: "+personalID.toString()
                displayPhone.text="ტელ:"+phoneNumber.toString()

            }else {
                Toast.makeText(this.requireContext(), "მომხმარებელი არ არებობს!", Toast.LENGTH_SHORT).show()
            }


        }.addOnFailureListener {
            Toast.makeText(this.requireContext(), "ხარვეზი!", Toast.LENGTH_SHORT).show()

        }


    }


}


        