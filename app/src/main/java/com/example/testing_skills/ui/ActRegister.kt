package com.example.testing_skills.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.testing_skills.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_register)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)


        //as declarações abaixo não seriam necessárias por conta do 'kotlin-android-extensions', mas prefiro lembrar do caminho longo
        regName = etRegisterUserName
        regEmail = etRegisterEmail
        regCPF = etRegisterCPF
        regCountry = etRegisterCountry
        regState = etRegisterState
        regCounty = etRegisterCounty
        regCEP = etRegisterCEP
        regStreet = etRegisterStreet
        regAdressNumber = etRegisterAddressNumber
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

            val name: String = etRegisterUserName.text.toString()
            val emailSF: String = etRegisterEmail.text.toString()
            val cpf: String = etRegisterCPF.text.toString()
            val country: String = etRegisterCountry.text.toString()
            val state: String = etRegisterState.text.toString()
            val county: String = etRegisterCounty.text.toString()
            val cep: String = etRegisterCEP.text.toString()
            val street: String = etRegisterStreet.text.toString()
            val address: String = etRegisterAddressNumber.text.toString()
            val compliment: String = etRegisterCompliment.text.toString()
            val pis: String = etRegisterPIS.text.toString()

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("NAME", name)
            editor.putString("EMAIL", emailSF)
            editor.putString("CPF", cpf)
            editor.putString("COUNTRY", country)
            editor.putString("STATE", state)
            editor.putString("COUNTY", county)
            editor.putString("CEP", cep)
            editor.putString("STREET", street)
            editor.putString("ADDRESS", address)
            editor.putString("COMPLIMENT", compliment)
            editor.putString("PIS", pis)
            editor.apply()


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