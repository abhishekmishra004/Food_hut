package com.org.asp.food_hut

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class Main_page : AppCompatActivity() {

    var LogINButton: ImageButton? = null
    var SignUPButton: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)
        LogINButton = findViewById(R.id.loginButton)
        SignUPButton = findViewById(R.id.signUpButton)

        clickHandler()
    }

    fun clickHandler() {
        LogINButton?.setOnClickListener({
            val startAct = Intent(this@Main_page, LogIn::class.java)
            startActivity(startAct)
            this.finish()
        })
        SignUPButton?.setOnClickListener({
            val startAct = Intent(this@Main_page, Signup::class.java)
            startActivity(startAct)
            this.finish()
        })

    }

}
