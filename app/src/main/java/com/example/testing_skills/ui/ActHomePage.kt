package com.example.testing_skills.ui

import android.content.Intent
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

    private lateinit var database : FirebaseDatabase
    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_home_page)

        val userId = intent.getStringExtra("user_id")
        val emailiId = intent.getStringExtra("email_id").toString()

        val userName = intent.getStringExtra("name").toString()
        val userCPF = intent.getStringExtra("cpf").toString()
        val userCountry = intent.getStringExtra("country").toString()
        val userState = intent.getStringExtra("state").toString()
        val userCounty = intent.getStringExtra("county").toString()
        val userCEP = intent.getStringExtra("cep").toString()
        val userStreet = intent.getStringExtra("street").toString()
        val userAdressNumber = intent.getStringExtra("adress_number").toString()
        val userCompliment = intent.getStringExtra("compliment").toString()
        val userPIS = intent.getStringExtra("pis").toString()


        welcome_message.text = "Olá ${userName}, seja bem-vindo! \n Seguem abaixo os seus dados:"

        if (userName.isNullOrEmpty() || emailiId.isNotEmpty() || userCPF.isNullOrEmpty() || userCountry.isNullOrEmpty()
            || userState.isNullOrEmpty() || userCounty.isNullOrEmpty() || userCEP.isNullOrEmpty() || userStreet.isNullOrEmpty() ||
                userAdressNumber.isNullOrEmpty() || userPIS.isNullOrEmpty() || userCompliment.isNullOrEmpty()){

            textView1.text = userName
            textView2.text = emailiId
            textView3.text = userCPF
            textView4.text = userCountry
            textView5.text = userState
            textView6.text = userCounty
            textView7.text = userCEP
            textView8.text = userStreet
            textView9.text = userAdressNumber
            textView10.text = userPIS
            textView11.text = userCompliment
        }else{
            textView1.text = ""
            textView2.text = emailiId
            textView3.text = ""
            textView4.text = ""
            textView5.text = ""
            textView6.text = ""
            textView7.text = ""
            textView8.text = ""
            textView9.text = ""
            textView10.text = ""
            textView11.text = ""
        }



        backScreen = homeNativeBackIcon


        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users")

        backScreen.setOnClickListener {
            startActivity(Intent(this,ActLogin::class.java))
            finish()
        }

        bt_logout.setOnClickListener{

            val user = FirebaseAuth.getInstance().currentUser

            user?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Usuário excluído com sucesso!", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(this,ActLogin::class.java))
            finish()
        }
    }
}