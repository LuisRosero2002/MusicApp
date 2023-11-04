package com.example.musicapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.musicapp.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private val GOOGLE_SING_IN = 100
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

       binding.google.setOnClickListener {
           val googleconf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(getString(R.string.default_web_client_id))
               .requestEmail()
               .build()

           val googleClient = GoogleSignIn.getClient(this, googleconf)
           googleClient.signOut()
           startActivityForResult(googleClient.signInIntent,GOOGLE_SING_IN)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SING_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account  = task.getResult(ApiException::class.java)

                if(account !== null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                        if(it.isSuccessful){
                            showHome(account.email ?: "")
                        }else{
                            showAlert()
                        }

                    }


                }
            }catch (e: ApiException){
                showAlert()
            }


        }
    }
}