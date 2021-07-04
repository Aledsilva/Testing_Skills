package com.example.testing_skills.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.testing_skills.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_act_home_page.*

class ActHomePage : AppCompatActivity() {

    lateinit var backScreen : ImageView
    lateinit var  preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_home_page)


        val userId = intent.getStringExtra("user_id")
        val emailiId = intent.getStringExtra("email_id")


        preferences = getSharedPreferences("SHARED_PREF" , Context.MODE_PRIVATE)

        val name = preferences.getString("NAME", "")
        textView1.text = name.toString()

        val emailSF = preferences.getString("EMAIL", "")
        textView2.text = emailSF.toString()

        val cpf = preferences.getString("CPF", "")
        textView3.text = cpf.toString()

        val country = preferences.getString("COUNTRY", "")
        textView4.text = country.toString()

        val state = preferences.getString("STATE", "")
        textView5.text = state.toString()

        val county = preferences.getString("COUNTY", "")
        textView6.text = county.toString()

        val cep = preferences.getString("CEP", "")
        textView7.text = cep.toString()

        val street = preferences.getString("STREET", "")
        textView8.text = street.toString()

        val address = preferences.getString("ADDRESS", "")
        textView9.text = address.toString()

        val compliment = preferences.getString("COMPLIMENT", "")
        if(compliment != null){
            textView10.text = compliment.toString()
        }

        val pis = preferences.getString("PIS", "")
        textView11.text = pis.toString()


        if(name != null){
            welcome_message.text = "Olá ${name}, seja bem-vindo! \n Seguem abaixo os seus dados:"
        }else{
            welcome_message.text = "Olá visitante, seja bem-vindo! \n Seguem abaixo os seus dados:"
        }




        backScreen = homeNativeBackIcon



        backScreen.setOnClickListener {
            startActivity(Intent(this,ActLogin::class.java))
            finish()
        }

        bt_logout.setOnClickListener{

            deleteFireUser()
            deleteSharedPref()
            startActivity(Intent(this,ActLogin::class.java))

        }

    }

    private fun deleteFireUser() {
        val user = FirebaseAuth.getInstance().currentUser

        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "DADOS APAGADOS!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteSharedPref() {

        textView1.text = ""
        textView2.text = ""
        textView3.text = ""
        textView4.text = ""
        textView5.text = ""
        textView6.text = ""
        textView7.text = ""
        textView8.text = ""
        textView9.text = ""
        textView10.text = ""
        textView11.text = ""
        welcome_message.text = "Olá visitante, seja bem-vindo! \n Seguem abaixo os seus dados:"

        val editor: SharedPreferences.Editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

}