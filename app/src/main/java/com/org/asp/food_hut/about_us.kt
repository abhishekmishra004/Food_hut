package com.org.asp.food_hut

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.widget.Toast

class about_us : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        var actionbarr : ActionBar? = this!!.supportActionBar
        actionbarr?.title = "About us"
        Toast.makeText(this,"about", Toast.LENGTH_SHORT).show()
    }
}
