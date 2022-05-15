package com.example.drcredit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit
import android.widget.Toast.makeText as toastMakeText

class introPage : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    lateinit var editText : EditText
    lateinit var getOtpButton : Button
    lateinit var progressBar: ProgressBar
    private var verificationId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_page)
        progressBar = findViewById(R.id.progressBar)
        editText = findViewById(R.id.etGetPhone)
        getOtpButton = findViewById(R.id.getOtpButton)
        getOtpButton.setOnClickListener(View.OnClickListener{
             var phoneNo = editText!!.getText().toString()
            if (TextUtils.isEmpty(editText!!.getText().toString())) {
                toastMakeText(
                    this,
                    "Please enter a phone number.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if(phoneNo.length< 10)
            {
                toastMakeText(
                    this,
                    "Please enter a valid phone number.",
                    Toast.LENGTH_SHORT
                ).show()

            }
            else
            {
                progressBar.visibility= View.VISIBLE
                getOtpButton.visibility = View.INVISIBLE
                mAuth = FirebaseAuth.getInstance()
                val phone = editText!!.getText().toString()
                val phoneVerify = "+91"+phone
                sendVerificationCode(phoneVerify)


            }
        })




    }
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber("+91"+editText!!.text.toString())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private val
            mCallBack: OnVerificationStateChangedCallbacks =
             object : OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                progressBar.visibility = View.GONE
                getOtpButton.visibility=View.VISIBLE
                verificationId = s
                var intent = Intent(applicationContext,otpVerification::class.java)
                val phone = editText!!.getText().toString().trim()
                intent.putExtra("phone",phone)
                intent.putExtra("verificationId",verificationId)
                startActivity(intent)
                finish()
            }

                 override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                     progressBar.visibility = View.GONE
                     getOtpButton.visibility=View.VISIBLE
                 }

                 override fun onVerificationFailed(p0: FirebaseException) {
                     progressBar.visibility=View.GONE
                     getOtpButton.visibility=View.VISIBLE
                     Toast.makeText(this@introPage, p0.message, Toast.LENGTH_SHORT).show()

                 }

                 }
             }