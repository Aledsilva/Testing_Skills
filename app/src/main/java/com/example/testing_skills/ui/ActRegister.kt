package com.example.testing_skills.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.testing_skills.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_act_register.*

class ActRegister : AppCompatActivity() {

    lateinit var regName : EditText
    lateinit var regEmail : EditText
    lateinit var regCountry : EditText
    lateinit var regCPF : EditText
    lateinit var regState : EditText
    lateinit var regCounty : EditText
    lateinit var regCEP : EditText
    lateinit var regStreet : EditText
    lateinit var regCompliment : EditText
    lateinit var regAdressNumber : EditText
    lateinit var regPIS : EditText
    lateinit var regPass : EditText
    lateinit var regButton : Button
    lateinit var backScreen : ImageView

    private lateinit var database : FirebaseDatabase
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_register)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Users")

        //as declarações abaixo não seriam necessárias por conta do 'kotlin-android-extensions', mas prefiro lembrar do caminho longo
        regName = etRegisterUserName
        regEmail = etRegisterEmail
        regCPF = etRegisterCPF
        regCountry = etRegisterCountry
        regState = etRegisterState
        regCounty = etRegisterCounty
        regCEP = etRegisterCEP
        regStreet = etRegisterStreet
        regAdressNumber = etRegisterAdressNumber
        regCompliment = etRegisterCompliment
        regPIS = etRegisterPIS
        regPass = etRegisterPassword
        backScreen = registerNativeBackIcon
        regButton = btRegister

        //Criando a máscara
        regCPF.addTextChangedListener(Mask.mask("###.###.###-##", regCPF))
        regCEP.addTextChangedListener(Mask.mask("#####-###", regCEP))
        regPIS.addTextChangedListener(Mask.mask("###.#####.##-#", regPIS))


        regButton.setOnClickListener {

            if (regName.text.isEmpty() || regEmail.text.isEmpty() || regCPF.text.isEmpty() || regCountry.text.isEmpty() ||
                    regState.text.isEmpty() || regCountry.text.isEmpty() || regCEP.text.isEmpty() || regStreet.text.isEmpty() ||
                    regAdressNumber.text.isEmpty() || regPIS.text.isEmpty() || regPass.text.isEmpty()){
                Toast.makeText(this,"Por favor, preencha todos os campos obrigatórios corretamente*",Toast.LENGTH_LONG).show()
            }else{

                val email: String = etRegisterEmail.text.toString().trim {it <= ' '}
                val password: String = etRegisterPassword.text.toString().trim {it <= ' '}

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            Toast.makeText(this, "Você foi registrado com sucesso!",
                                Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, ActHomePage::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", firebaseUser.uid)
                            intent.putExtra("email_id", email)

                            intent.putExtra("name",regName.text.toString())
                            intent.putExtra("cpf",regCPF.text.toString())
                            intent.putExtra("country",regCountry.text.toString())
                            intent.putExtra("state",regState.text.toString())
                            intent.putExtra("county",regCounty.text.toString())
                            intent.putExtra("cep",regCEP.text.toString())
                            intent.putExtra("street",regStreet.text.toString())
                            intent.putExtra("adress_number",regAdressNumber.text.toString())
                            intent.putExtra("compliment",regCompliment.text.toString())
                            intent.putExtra("pis",regPIS.text.toString())
                            startActivity(intent)
                            finish()

                        }else{
                            Toast.makeText(this, "Erro ao efetuar Login!",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        backScreen.setOnClickListener {
            onBackPressed()
        }
    }
}