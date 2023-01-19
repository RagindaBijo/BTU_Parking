package com.example.carrent.InnerFragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.carrent.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class InnerFragmentSecond : Fragment(R.layout.fragment_inner_second) {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("Users")

    private lateinit var editMoney:EditText
    private lateinit var plusButton:Button
    private lateinit var moneys:String

    private lateinit var builder:AlertDialog.Builder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editMoney=view.findViewById(R.id.editMoney)
        plusButton=view.findViewById(R.id.plusButton)


        builder=AlertDialog.Builder(this.requireContext())


        plusButton.setOnClickListener{

            moneys=editMoney.text.toString()

            if (moneys.isEmpty()||moneys=="0.0"||moneys=="0"||moneys=="0."){
                Toast.makeText(this.requireContext(), "მიუთითეთ სწორი რაოდენობა!", Toast.LENGTH_SHORT).show()
            }else {

                builder.setTitle("Alert!")
                    .setMessage("დარწმუნებული ხართ, რომ გსურთ თანხის შეტანა?")
                    .setCancelable(true)
                    .setPositiveButton("დიახ"){dialogInterface,it->

                        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {

                            if (it.exists()) {

                                val moneyAmmountIn = it.child("moneyAmmount").value.toString()
                                val moneyAmmount=(moneyAmmountIn.toDouble()+moneys.toDouble()).toString()
                                val name = it.child("name").value.toString()
                                val surName  = it.child("surName").value.toString()
                                val personalID = it.child("personalID").value.toString()
                                val phoneNumber  = it.child("phoneNumber").value.toString()


                                val changeInfo = PersonInfo(moneyAmmount,name,surName,personalID,phoneNumber)
                                db.child(auth.currentUser?.uid!!).setValue(changeInfo).addOnSuccessListener {

                                    editMoney.text.clear()
                                    Toast.makeText(this.requireContext(), "წარმატებით შეავსეთ ანგარიში", Toast.LENGTH_SHORT).show()


                                }.addOnFailureListener {
                                    Toast.makeText(this.requireContext(), "ხარვეზი!", Toast.LENGTH_SHORT).show()
                                }

                            }else {
                                Toast.makeText(this.requireContext(), "მომხმარებელი არ არებობს!", Toast.LENGTH_SHORT).show()
                            }

                        }.addOnFailureListener {
                            Toast.makeText(this.requireContext(), "ხარვეზი!", Toast.LENGTH_SHORT).show()

                        }

                    }.setNegativeButton("არა"){dialogInterface,it->
                        dialogInterface.cancel()
                    }.show()

            }

        }






    }


}


        