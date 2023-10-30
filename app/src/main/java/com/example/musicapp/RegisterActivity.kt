package com.example.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        /*
        btnLogin.setOnClickListener{

            val emailS = email.text.toString()
            val passwordS = password.text.toString()

            if (emailS.isNotEmpty() && passwordS.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailS,passwordS).addOnCompleteListener{
                    if(it.isSuccessful){
                        //showHome(it.result?.user?.email ?:"")
                    }else{
                        showAlert()
                    }
                }
            }

        }

 */
    }
}