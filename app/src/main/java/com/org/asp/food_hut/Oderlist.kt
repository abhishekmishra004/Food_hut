package com.org.asp.food_hut

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_oderlist.*

class Oderlist : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oderlist)

        textView1.setOnClickListener({
            var start = Intent(this@Oderlist,Restro_side_dishes::class.java)
            start.putExtra("pos","1")
            startActivity(start)
        })
        textView2.setOnClickListener({
            var start = Intent(this@Oderlist,Restro_side_dishes::class.java)
            start.putExtra("pos","2")
            startActivity(start)
        })
        textView3.setOnClickListener({
            var start = Intent(this@Oderlist,Restro_side_dishes::class.java)
            start.putExtra("pos","3")
            startActivity(start)
        })
        textView4.setOnClickListener({
            var start = Intent(this@Oderlist,Restro_side_dishes::class.java)
            start.putExtra("pos","4")
            startActivity(start)
        })
        textView5.setOnClickListener({
            var start = Intent(this@Oderlist,Restro_side_dishes::class.java)
            start.putExtra("pos","5")
            startActivity(start)
        })
        textView6.setOnClickListener({
            var start = Intent(this@Oderlist,Restro_side_dishes::class.java)
            start.putExtra("pos","6")
            startActivity(start)
        })
        textView7.setOnClickListener({
            var start = Intent(this@Oderlist,Restro_side_dishes::class.java)
            start.putExtra("pos","7")
            startActivity(start)
        })


    }
}
