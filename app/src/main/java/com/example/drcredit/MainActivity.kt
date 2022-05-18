package com.example.drcredit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedpreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedpreferences = getSharedPreferences("drFile", MODE_PRIVATE)
        val status = sharedpreferences.getBoolean("isLoggedIn",false)
        setContentView(R.layout.activity_main)
        var timer = Timer()
        timer.schedule(object : TimerTask() {

            override fun run() {
                if(status)
                {
                    var intent = Intent(applicationContext,otpVerification::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    var intent = Intent(applicationContext, introPage::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 5000)
    }
}