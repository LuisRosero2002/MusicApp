package com.example.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.musicapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = binding.etEmail
        val password = binding.etPassword

        register(email,password)

    }

    private fun register(email:EditText,password:EditText) {

       binding.btnCreateUser.setOnClickListener{

           val emailS = email.text.toString()
           val passwordS = password.text.toString()

           if (emailS.isNotEmpty() && passwordS.isNotEmpty()){
               FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailS,passwordS).addOnCompleteListener{
                   if(it.isSuccessful){
                       showHome(it.result?.user?.email ?:"")
                   }else{
                       showAlert()
                   }
               }
           }

       }

    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al autenticar")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email:String){
        val homeIntent: Intent = Intent(this, HomeActivity::class.java)
        homeIntent.putExtra("email",email)
        startActivity(homeIntent)
    }
}