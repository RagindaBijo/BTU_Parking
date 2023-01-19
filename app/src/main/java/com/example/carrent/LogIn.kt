package com.example.carrent

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import com.example.carrent.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var check:CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email=binding.loginEmail
        val password=binding.loginPassword
        val logInBtn=binding.login
        val signInBtn=binding.signup
        val forgotPassword=binding.forget

        check=findViewById(R.id.checkBox)

        loadData()


        if (check.isChecked){
            if (FirebaseAuth.getInstance().currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }



        logInBtn.setOnClickListener {

            val rMail=email.text.toString()
            val rPassword=password.text.toString()

            if (rMail.isEmpty()||rPassword.isEmpty()){
                Toast.makeText(this,"გრაფა ცარიელია!", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(rMail,rPassword)
                .addOnCompleteListener {task->
                    if (task.isSuccessful){
                        val mainPage= Intent(this,MainActivity::class.java)
                        startActivity(mainPage)
                        finish()
                    }else{
                        Toast.makeText(this,"ხარვეზი!",Toast.LENGTH_SHORT).show()
                    }
                }

        }

        signInBtn.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
            finish()
        }


        forgotPassword.setOnClickListener {
            val forget=Intent(this,ForgotPassword::class.java)
            startActivity(forget)
        }

        check.setOnClickListener{
            saveData()
        }


    }


    private fun saveData() {
        val sharedPreference=getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor=sharedPreference.edit()
        editor.apply {
            putBoolean("CHECK_KEY",check.isChecked)
        }.apply()
    }

    private fun loadData() {
        val sharedPreferences=getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedCheck=sharedPreferences.getBoolean("CHECK_KEY", false)
        check.isChecked=savedCheck
    }


}