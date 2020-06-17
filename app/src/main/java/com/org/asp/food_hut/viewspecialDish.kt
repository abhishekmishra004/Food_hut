package com.org.asp.food_hut

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_viewspecial_dish.*

class viewspecialDish : AppCompatActivity() {

    var ide: String? = null
    var dishname: String? = null
    var dishcost: String? = null
    var button: Button? = null
    var Rest: TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewspecial_dish)
        button = findViewById(R.id.ButtonAddCartee)
        Rest = findViewById(R.id.Resto_name)
        var Restname:String ?=null
        val mIntent = intent
        var bun: Bundle = mIntent.getBundleExtra("val")
        var pos = bun.getString("posi")
        ide = bun.getString("userid")
        Toast.makeText(this@viewspecialDish, ide, Toast.LENGTH_LONG).show()
        if (pos == "1") {
            val rootRef = FirebaseDatabase.getInstance().reference
            val checkUser = rootRef.child("Dishes/1/1")
            val listner = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    var post = p0.getValue(special::class.java)
                    var url = post?.geturl()
                    dishname = post?.getname()
                    dishcost = post?.getcost()
                    Restname=post?.RestroName
                    Rest?.text = post?.RestroName
                    Toast.makeText(this@viewspecialDish,Restname,Toast.LENGTH_SHORT).show()
                    Dish_namee.text = post?.getname()
                    Dish_Costee.text = post?.getcost()
                    Dish_detailsee.text = post?.getdetails()
                    Picasso.get().load(url).into(Dish_imagee)
                }

            }
            checkUser.addListenerForSingleValueEvent(listner)

        } else if (pos == "2") {
            val rootRef = FirebaseDatabase.getInstance().reference
            val checkUser = rootRef.child("Dishes/1/3")
            val listner = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var post = p0.getValue(special::class.java)
                    var url = post?.geturl()
                    dishname = post?.getname()
                    dishcost = post?.getcost()
                    Restname=post?.RestroName
                    Rest?.text = post?.RestroName
                    Toast.makeText(this@viewspecialDish,Restname,Toast.LENGTH_SHORT).show()
                    Dish_namee.text = post?.getname()
                    Dish_Costee.text = post?.getcost()
                    Dish_detailsee.text = post?.getdetails()
                    Picasso.get().load(url).into(Dish_imagee)
                }

            }
            checkUser.addListenerForSingleValueEvent(listner)

        } else if (pos == "3") {
            val rootRef = FirebaseDatabase.getInstance().reference
            val checkUser = rootRef.child("Dishes/3/5")
            val listner = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var post = p0.getValue(special::class.java)
                    var url = post?.geturl()
                    dishname = post?.getname()
                    dishcost = post?.getcost()
                    Restname=post?.RestroName
                    Toast.makeText(this@viewspecialDish,Restname,Toast.LENGTH_SHORT).show()
                    Rest?.text = post?.RestroName
                    Dish_namee.text = post?.getname()
                    Dish_Costee.text = post?.getcost()
                    Dish_detailsee.text = post?.getdetails()
                    Picasso.get().load(url).into(Dish_imagee)
                }

            }
            checkUser.addListenerForSingleValueEvent(listner)
        } else if (pos == "4") {
            val rootRef = FirebaseDatabase.getInstance().reference
            val checkUser = rootRef.child("Dishes/4/3")
            val listner = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var post = p0.getValue(special::class.java)
                    var url = post?.geturl()
                    dishname = post?.getname()
                    dishcost = post?.getcost()
                    Restname=post?.RestroName
                    Toast.makeText(this@viewspecialDish,Restname,Toast.LENGTH_SHORT).show()
                    Rest?.text = post?.RestroName
                    var st = "Rs "+post?.Dishcost
                    Dish_namee.text = post?.getname()
                    Dish_Costee.text = st
                    Dish_detailsee.text = post?.getdetails()
                    Picasso.get().load(url).into(Dish_imagee)
                }

            }
            checkUser.addListenerForSingleValueEvent(listner)
        } else if (pos == "5") {
            val rootRef = FirebaseDatabase.getInstance().reference
            val checkUser = rootRef.child("Dishes/6/4")
            val listner = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var post = p0.getValue(special::class.java)
                    var url = post?.geturl()
                    dishname = post?.getname()
                    dishcost = post?.getcost()
                    Restname=post?.RestroName
                    Toast.makeText(this@viewspecialDish,Restname,Toast.LENGTH_SHORT).show()
                    Rest?.text = post?.RestroName
                    Dish_namee.text = post?.getname()
                    Dish_Costee.text = post?.getcost()
                    Dish_detailsee.text = post?.getdetails()
                    Picasso.get().load(url).into(Dish_imagee)
                }

            }
            checkUser.addListenerForSingleValueEvent(listner)

        }
        button?.setOnClickListener {


            val ref = FirebaseDatabase.getInstance().getReference("UsersCart/$ide")
            var dishid = ref.push().key

                var carte = viewSpecialDIshModel(dishcost!!, dishname!!,ide!!,Restname!!)
                ref.child(dishid!!).setValue(carte).addOnCompleteListener {
                    Toast.makeText(this@viewspecialDish, "Item added to cart goto->home->Mycart", Toast.LENGTH_LONG).show()
                    this@viewspecialDish.finish()
                }
        }
    }
}


