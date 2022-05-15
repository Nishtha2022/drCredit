package com.example.drcredit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class otpVerification : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
     var editText1: EditText? = null
     var editText2: EditText? = null
     var editText3: EditText? = null
     var editText4: EditText?= null
     var editText5: EditText? = null
     var editText6: EditText? = null
     lateinit var progressBar : ProgressBar
     lateinit var verifyBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_otp_verification)

        var textView : TextView = findViewById(R.id.textView2)
        var str = intent.getStringExtra("phone")?:"12345"
        textView.text = str
        startTimeCounter(this)
        progressBar = findViewById(R.id.progressBar2)
        verifyBtn  = findViewById(R.id.btnVerify)
        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        editText3 = findViewById(R.id.editText3)
        editText4 = findViewById(R.id.editText4)
        editText5 = findViewById(R.id.editText5)
        editText6 = findViewById(R.id.editText6)

        setupOtpInput()


        verifyBtn.setOnClickListener(View.OnClickListener() {

          if(editText1!!.text.toString().trim().isEmpty()
              || editText2!!.text.toString().trim().isEmpty()
              || editText3!!.text.toString().trim().isEmpty()
              || editText4!!.text.toString().trim().isEmpty()
              || editText5!!.text.toString().trim().isEmpty()
              || editText6!!.text.toString().trim().isEmpty())
          {
              Toast.makeText(this,"please enter valid otp",Toast.LENGTH_SHORT).show()

          }
            else
            {
              progressBar.visibility = View.VISIBLE
              verifyBtn.visibility = View.GONE
                mAuth = FirebaseAuth.getInstance()
              var id = intent.getStringExtra("verificationId")
              var code:String = editText1!!.text.toString() +
                      editText2!!.text.toString() +
                      editText3!!.text.toString() +
                      editText4!!.text.toString() +
                      editText5!!.text.toString() +
                      editText6!!.text.toString()
              if(id!=null)
              {
                verifyCode(code,id)
              }

          }
        })

    }

    fun setupOtpInput() {


        val textWatcher1 = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    editText2!!.requestFocus()
                    editText2!!.setBackgroundResource(R.drawable.focus_otp_background)
                    editText1!!.setBackgroundResource(R.drawable.otp_background)
                }
            }
        }
        val textWatcher2 = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    editText3!!.requestFocus()
                    editText3!!.setBackgroundResource(R.drawable.focus_otp_background)
                    editText2!!.setBackgroundResource(R.drawable.otp_background)
                }
            }
        }
        val textWatcher3 = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    editText4!!.requestFocus()
                    editText4!!.setBackgroundResource(R.drawable.focus_otp_background)
                    editText3!!.setBackgroundResource(R.drawable.otp_background)
                }
            }
        }
        val textWatcher4 = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    editText5!!.requestFocus()
                    editText5!!.setBackgroundResource(R.drawable.focus_otp_background)
                    editText4!!.setBackgroundResource(R.drawable.otp_background)
                }
            }
        }
        val textWatcher5 = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    editText6!!.requestFocus()
                    editText6!!.setBackgroundResource(R.drawable.focus_otp_background)
                    editText5!!.setBackgroundResource(R.drawable.otp_background)
                }
            }
        }

        editText1!!.addTextChangedListener(textWatcher1)
        editText2!!.addTextChangedListener(textWatcher2)
        editText3!!.addTextChangedListener(textWatcher3)
        editText4!!.addTextChangedListener(textWatcher4)
        editText5!!.addTextChangedListener(textWatcher5)
    }
    fun startTimeCounter(view: otpVerification) {
        var counter = 30
        val countTime: TextView = findViewById(R.id.timer)
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countTime.text = "00:"+counter.toString()+" sec"
                counter--
            }
            override fun onFinish() {
                countTime.text = "Resend OTP"
            }
        }.start()
    }

    fun verifyCode(code :String,verificationId:String)
    {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }


    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val i = Intent(this@otpVerification, creditScoreDetail::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                }
            }

    }

}

