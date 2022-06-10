package com.example.tarea

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.example.tarea.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing firebase
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        // Assigning the methods to the buttons
        binding.btInicio.setOnClickListener{
            Inicio()
        }
        binding.BtAgregar.setOnClickListener {
            registrar()
        }
    }

    private fun update(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        update(user)
    }

    private fun Inicio() {
        val email = binding.etCorreo.text.toString()
        val password = binding.etContra.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->
            if (task.isSuccessful) {
                Log.d("Login user", "Success")
                val user = auth.currentUser
                update(user)
            } else {
                Log.d("Login user", "Failed")
                Toast.makeText(baseContext, "Fail", Toast.LENGTH_LONG).show()
                update(null)
            }
        }
    }

    private fun registrar() {
        val email = binding.etCorreo.text.toString()
        val password = binding.etContra.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->
            if (task.isSuccessful) {
                Log.d("Creating user", "Registration")
                val user = auth.currentUser
                update(user)
            } else {
                Log.d("Creating user", "Failed")
                Toast.makeText(baseContext, "Fail", Toast.LENGTH_LONG).show()
                update(null)
            }
        }

    }
}