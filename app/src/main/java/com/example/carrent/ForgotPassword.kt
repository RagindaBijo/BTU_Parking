package com.example.carrent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carrent.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        supportActionBar?.hide()
        binding= ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val verifyMail=binding.verifyEmail
        val verifyBtn=binding.verify

        verifyBtn.setOnClickListener {

            val rVerifyMail=verifyMail.text.toString()


            if (rVerifyMail.isEmpty()){
                Toast.makeText(this,"გრაფა ცარიელია!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(rVerifyMail)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Verification mail successfully sent!", Toast.LENGTH_SHORT).show()
                        val resetPassword= Intent(this,LogIn::class.java)
                        startActivity(resetPassword)
                        finish()
                    }else{
                        Toast.makeText(this,"ხარვეზი!", Toast.LENGTH_SHORT).show()

                    }
                }

        }


    }
}