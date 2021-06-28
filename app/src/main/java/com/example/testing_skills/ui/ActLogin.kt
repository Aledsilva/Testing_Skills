package com.example.testing_skills.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.testing_skills.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_act_login.*

class ActLogin : AppCompatActivity() {

    companion object {
        private const val CODE_SIGN_IN = 120
    }

    lateinit var logEmail: EditText
    lateinit var logPass: EditText
    lateinit var registerTxt: TextView
    lateinit var logButton: Button
    lateinit var backScreen: ImageView

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_login)

        //as declarações abaixo não seriam necessárias por conta do 'kotlin-android-extensions', mas prefiro lembrar do caminho longo
        logEmail = etLoginEmail
        logPass = etLoginPassword
        registerTxt = txtRegisterButton
        logButton = btLogin
        backScreen = loginNativeBackIcon

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btGoogle.setOnClickListener {
            signIn()
        }


        registerTxt.setOnClickListener {
            var goToRegiterAct = Intent(this, ActRegister::class.java)
            startActivity(goToRegiterAct)
        }

        logButton.setOnClickListener {
            when {
                TextUtils.isEmpty(etLoginEmail.text.toString().trim(){it <= ' '}) ->{
                    Toast.makeText(
                        this,
                        "Por favor, insira um E-Mail",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(etLoginPassword.text.toString().trim(){it <= ' '}) ->{
                    Toast.makeText(
                        this,
                        "Por favor, insira uma senha",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val email: String = etLoginEmail.text.toString().trim {it <= ' '}
                    val password: String = etLoginPassword.text.toString().trim {it <= ' '}


                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(this, "Você foi registrado com sucesso!",
                                    Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, ActHomePage::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id", auth.currentUser?.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()

                            }else{
                                Toast.makeText(this, task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }



        backScreen.setOnClickListener {
            onBackPressed()
        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, CODE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == CODE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account!!)
                } catch (e: ApiException) {
                    Log.w("SiginIn", "Google sign in failed", e)
                }
            } else {
                Log.w("SiginIn", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("SiginIn", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("SiginIn", "signInWithCredential:success")
                    val intent = Intent(this, ActHomePage::class.java)
                    startActivity(intent)
                } else {
                    Log.w("SiginIn", "signInWithCredential:failure", task.exception)
                }
            }
    }
}