package com.example.carrent.fragments

import  android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carrent.LogIn
import com.example.carrent.OrderInfo
import com.example.carrent.PersonInfo
import com.example.carrent.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar


class TimeFragment : Fragment(R.layout.fragment_time) {

    private val auth = FirebaseAuth.getInstance()
    private val pd = FirebaseDatabase.getInstance().getReference("Order_Info")
    private val db = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var builder: AlertDialog.Builder
    private lateinit var spot: TextView
    private lateinit var photo: ImageView
    private lateinit var plusText: TextView
    private lateinit var minusText: TextView
    private lateinit var timeText: TextView
    private lateinit var costText: TextView
    private lateinit var timeBookButton: Button
    private lateinit var delete: Button
    private lateinit var moneyInWallet:TextView
    private lateinit var orderName:TextView
    private lateinit var orderTime:TextView
    private var timeNumberResult = 0
    private var costNumberResult = 0
    private var number="0"
    private lateinit var sum:String
    private var moneyShow=0.0


    private lateinit var calendar:Calendar
    private lateinit var simpleDataFormat:SimpleDateFormat
    private lateinit var simpleDataFormatM:SimpleDateFormat
    private lateinit var simpleDataFormatH:SimpleDateFormat
    private lateinit var data:String
    private lateinit var dataM:String
    private lateinit var dataH:String
    private lateinit var spotOrderNameData:String
    private lateinit var spotOrderTimeData:String




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        plusText = view.findViewById(R.id.plus)
        minusText = view.findViewById(R.id.minus)
        timeText = view.findViewById(R.id.timenumber)
        costText = view.findViewById(R.id.pricenumber)
        timeBookButton = view.findViewById(R.id.book)
        spot = view.findViewById(R.id.spot)
        photo = view.findViewById(R.id.photo)
        orderName=view.findViewById(R.id.orderName)
        orderTime=view.findViewById(R.id.orderTime)
        delete=view.findViewById(R.id.delete)
        moneyInWallet=view.findViewById(R.id.moneyInWallet)
        builder=AlertDialog.Builder(this.requireContext())



        pd.child(auth.currentUser?.uid!!).get().addOnSuccessListener {

            if (it.exists()) {
                val orderNameRecieved = it.child("spotOrderName").value
                val orderTimeRecieved = it.child("spotOrderTime").value

                orderName.text=orderNameRecieved.toString()
                orderTime.text=orderTimeRecieved.toString()
            }

        }.addOnFailureListener {
            Toast.makeText(this.requireContext(), "ხარვეზი!", Toast.LENGTH_SHORT).show()

        }





        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {

            if (it.exists()) {
                val moneyAmmountIn = it.child("moneyAmmount").value
                moneyInWallet.text=moneyAmmountIn.toString()
            }else {
                Toast.makeText(this.requireContext(), "მომხმარებელი არ არებობს!", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener {
            Toast.makeText(this.requireContext(), "ხარვეზი!", Toast.LENGTH_SHORT).show()

        }



        number = TimeFragmentArgs.fromBundle(requireArguments()).num

        if (number == "1") {
           spot.text = TimeFragmentArgs.fromBundle(requireArguments()).spotNameResult
            photo.setImageResource(TimeFragmentArgs.fromBundle(requireArguments()).spotImage)
        }

        plusText.setOnClickListener {
            if (timeNumberResult<24){
                timeNumberResult += 1
                timeText.text = timeNumberResult.toString()
                costNumberResult += 2
                costText.text = costNumberResult.toString()
            }

        }


        minusText.setOnClickListener {
            if (timeNumberResult != 0) {
                timeNumberResult -= 1
                timeText.text = timeNumberResult.toString()
                costNumberResult -= 2
                costText.text = costNumberResult.toString()
            }

        }


        delete.setOnClickListener{
            if (orderName.text=="ჯავშანი არ იძებნება"){
                Toast.makeText(this.requireContext(), "ჯავშანი არ არსებოს!", Toast.LENGTH_SHORT).show()
            }
            else{
                builder.setTitle("Alert!")
                    .setMessage("დარწმუნებული ხართ, რომ გსურთ ჯავშნის გაუქმება?")
                    .setCancelable(true)
                    .setPositiveButton("დიახ"){dialogInterface,it->
                        pd.child(auth.currentUser?.uid!!).removeValue()
                        Toast.makeText(this.requireContext(), "ჯავშანი გაუქმებულია!", Toast.LENGTH_SHORT).show()
                        orderName.text="ჯავშანი არ იძებნება"
                        orderTime.text=""
                    }.setNegativeButton("არა"){dialogInterface,it->
                        dialogInterface.cancel()
                    }.show()
            }

        }

        timeBookButton.setOnClickListener {

            if (number =="0") {
                val action = TimeFragmentDirections.actionTimeFragmentToRentalFragment()
                findNavController().navigate(action)
            }else if(orderName.text!="ჯავშანი არ იძებნება"){
                Toast.makeText(this.requireContext(), "თქვენ უკვე გაქვთ მოქმედი ჯავშანი", Toast.LENGTH_SHORT).show()
            }else if (number=="1"){

                if (moneyInWallet.text.toString().toDouble() >= costText.text.toString().toDouble()){
                    builder.setTitle("Alert!")
                        .setMessage("დარწმუნებული ხართ, რომ გსურთ დაჯავშნოთ ეს ადგილი?")
                        .setCancelable(true)
                        .setPositiveButton("დიახ"){dialogInterface,it->
                            Toast.makeText(this.requireContext(), "თქვენ წარმატებით დაჯავშნეთ ადგილი!", Toast.LENGTH_SHORT).show()
                            sum= (moneyInWallet.text.toString().toDouble()-costText.text.toString().toDouble()).toString()
                            moneyInWallet.text=sum


                            db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {

                                if (it.exists()) {

                                    val moneyAmmount=(moneyInWallet.text.toString() .toDouble()-costText.text.toString().toDouble()).toString()
                                    val name = it.child("name").value.toString()
                                    val surName  = it.child("surName").value.toString()
                                    val personalID = it.child("personalID").value.toString()
                                    val phoneNumber  = it.child("phoneNumber").value.toString()


                                    val changeInfo = PersonInfo(moneyAmmount,name,surName,personalID,phoneNumber)
                                    db.child(auth.currentUser?.uid!!).setValue(changeInfo).addOnFailureListener {
                                        Toast.makeText(this.requireContext(), "ხარვეზი!", Toast.LENGTH_SHORT).show()
                                    }

                                }else {
                                    Toast.makeText(this.requireContext(), "მომხმარებელი არ არებობს!", Toast.LENGTH_SHORT).show()
                                }

                            }.addOnFailureListener {
                                Toast.makeText(this.requireContext(), "ხარვეზი!", Toast.LENGTH_SHORT).show()

                            }




                            costText.text="0"
                            costNumberResult=0
                            timeNumberResult=0

                            val fullTime=timeText.text.toString()
                            calendar=Calendar.getInstance()
                            simpleDataFormat= SimpleDateFormat("HH:mm")
                            data=simpleDataFormat.format(calendar.time)
                            simpleDataFormatM=SimpleDateFormat("mm")
                            dataM=simpleDataFormatM.format(calendar.time)
                            simpleDataFormatH=SimpleDateFormat("HH")
                            dataH=simpleDataFormatH.format(calendar.time)
                            timeText.text="0"

                            orderName.text=TimeFragmentArgs.fromBundle(requireArguments()).spotNameResult
                            if (dataH.toInt()+fullTime.toInt()<24){
                                orderTime.text="$data - ${dataH.toInt()+fullTime.toInt()}:$dataM"
                            }else{
                                if (dataH.toInt()+fullTime.toInt()-24<10){
                                    orderTime.text="$data - 0${dataH.toInt()+fullTime.toInt()-24}:$dataM"

                                }else{
                                    orderTime.text="$data - ${dataH.toInt()+fullTime.toInt()-24}:$dataM"
                                }


                            }



                            spotOrderNameData= TimeFragmentArgs.fromBundle(requireArguments()).spotNameResult
                            spotOrderTimeData = "$data - ${dataH.toInt()+fullTime.toInt()}:$dataM"

                            if (dataH.toInt()+fullTime.toInt()<24){
                                spotOrderTimeData="$data - ${dataH.toInt()+fullTime.toInt()}:$dataM"
                            }else{
                                if (dataH.toInt()+fullTime.toInt()-24<10){
                                    spotOrderTimeData="$data - 0${dataH.toInt()+fullTime.toInt()-24}:$dataM"

                                }else{
                                    spotOrderTimeData="$data - ${dataH.toInt()+fullTime.toInt()-24}:$dataM"
                                }
                            }

                            val personInfo = OrderInfo(spotOrderNameData, spotOrderTimeData)
                            pd.child(auth.currentUser?.uid!!).setValue(personInfo).addOnSuccessListener {

                            }.addOnFailureListener {
                                Toast.makeText(this.requireContext(), "ხარვეზი!", Toast.LENGTH_SHORT).show()
                            }

                        }.setNegativeButton("არა"){dialogInterface,it->
                            dialogInterface.cancel()
                        }.show()
                }else if (moneyInWallet.text.toString().toDouble() < costText.text.toString().toDouble()){
                    Toast.makeText(this.requireContext(), "არასაკმარისი თანხა", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

}