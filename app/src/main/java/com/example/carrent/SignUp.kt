package com.example.carrent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.carrent.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        supportActionBar?.hide()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val regEmail = binding.regEmail
        val regPassword = binding.regPassword
        val regRepeatPassword = binding.repeatPassword
        val signUp = binding.signUpBtn

        signUp.setOnClickListener {

            val rRegEmail = regEmail.text.toString()
            val rRegPassword = regPassword.text.toString()
            val rRepeatPassword = regRepeatPassword.text.toString()

            if (rRegEmail.isEmpty() || rRegPassword.isEmpty() || rRepeatPassword.isEmpty()) {
                Toast.makeText(this, "Empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (rRepeatPassword != rRegPassword) {
                Toast.makeText(this, "Repeat password correctly!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(rRegEmail, rRegPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val register = Intent(this, RegistrationInfo::class.java)
                            startActivity(register)
                            finish()
                        } else {
                            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }

    }
}