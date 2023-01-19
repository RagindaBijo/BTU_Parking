package com.example.carrent.fragments

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources.NotFoundException
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.carrent.*
import com.example.carrent.InnerFragment.InnerFragmentFirst
import com.example.carrent.InnerFragment.InnerFragmentSecond
import com.example.carrent.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager:ViewPager2
    private lateinit var logOutBtn: Button
    private lateinit var changePassword: Button
    private lateinit var binding: ActivityMainBinding

    private lateinit var builder:AlertDialog.Builder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logOutBtn = view.findViewById(R.id.logout)
        changePassword = view.findViewById(R.id.changePassword)
        tabLayout=view.findViewById(R.id.tabLayout2)
        viewPager=view.findViewById(R.id.viewPager)
        viewPager.adapter=ViewPagerAdapter(this.requireActivity())
        TabLayoutMediator(tabLayout,viewPager){tab,index->
            tab.text=when(index){
                0->{"პროფილი"}
                1->{"ჩარიცხვა"}
                else -> {throw NotFoundException("ხარვეზი")}
            }

        }.attach()



        builder=AlertDialog.Builder(this.requireContext())


        logOutBtn.setOnClickListener {

            builder.setTitle("Alert!")
                .setMessage("დარწმუნებული ხართ, რომ გსურთ აქაუნთიდან გასვლა?")
                .setCancelable(true)
                .setPositiveButton("დიახ"){dialogInterface,it->
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this.requireContext(), LogIn::class.java)
                    startActivity(intent)
                }.setNegativeButton("არა"){dialogInterface,it->
                    dialogInterface.cancel()
                }.show()

        }



        changePassword.setOnClickListener {
            val forget=Intent(this.requireContext(), ForgotPassword::class.java)
            startActivity(forget)
        }





    }


}

