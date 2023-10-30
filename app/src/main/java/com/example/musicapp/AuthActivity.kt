package com.example.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val email = findViewById<EditText>(R.id.etLogin)
        val password = findViewById<EditText>(R.id.etPassword)

        authenticated(email,password)

    }

    private fun authenticated(email:EditText,password:EditText){

        //val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnForgotpass = findViewById<Button>(R.id.btnForgotPass)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener{

            val emailS = email.text.toString()
            val passwordS = password.text.toString()

            if (emailS.isNotEmpty() && passwordS.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailS,passwordS).addOnCompleteListener{
                    if(it.isSuccessful){
                        showHome(it.result?.user?.email ?:"")
                    }else{
                        showAlert()
                    }
                }
            }

        }

        btnRegister.setOnClickListener{
            showRegister()
        }

        btnForgotpass.setOnClickListener{
            val emailS = email.text.toString()
            showForgotPass(emailS)
        }

    }

    private fun showForgotPass(email:String) {

    }

    private fun showRegister() {
        val registerIntent:Intent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
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