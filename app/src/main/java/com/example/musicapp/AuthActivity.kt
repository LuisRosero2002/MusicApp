package com.example.musicapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.musicapp.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screenSplash.setKeepOnScreenCondition{false}

        val email = binding.etLogin
        val password = binding.etPassword
/*
        authenticated(email,password)
        session()

 */
    }

    override fun onStart() {
        super.onStart()

        binding.authLayout.visibility = View.VISIBLE
    }
    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email",null)

        if(email != null){
            binding.authLayout.visibility = View.INVISIBLE
            showHome(email)
        }
    }

    private fun authenticated(email:EditText,password:EditText){


        binding.btnLogin.setOnClickListener{

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

        binding.btnRegister.setOnClickListener{
            showRegister()
        }

        binding.btnForgotPass.setOnClickListener{
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