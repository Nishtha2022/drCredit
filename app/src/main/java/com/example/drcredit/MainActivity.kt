package com.example.drcredit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                var intent = Intent(applicationContext,introPage::class.java)
                startActivity(intent)
                finish()
            }
        }, 5000)
    }
}