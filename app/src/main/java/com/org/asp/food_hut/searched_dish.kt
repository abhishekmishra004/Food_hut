package com.org.asp.food_hut

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_searched_dish.*

class searched_dish : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searched_dish)

        val mIntent = intent
        var bun: Bundle = mIntent.getBundleExtra("bun")
        var link = bun.getString("link")
        var value =bun.getString("val")
        var dishcost = String()
        var dishname = String()
        var restname = String()
        val rootRef = FirebaseDatabase.getInstance().reference
        val checkUser = rootRef.child(link)
        val listner = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var post = p0.getValue(special::class.java)
                var url = post?.geturl()
                dishcost= post?.Dishcost!!
                dishname = post?.Dishname!!
                restname = post?.RestroName!!
                sResto_name.text = post?.RestroName
                Picasso.get().load(url).into(sDish_imagee)
                sDish_namee.text =post?.Dishname
                sDish_Costee.text = post?.Dishcost
                sDish_detailsee.text =post?.Dishdetails
            }
        }
        checkUser.addListenerForSingleValueEvent(listner)

        sButtonAddCartee.setOnClickListener({
            val ref = FirebaseDatabase.getInstance().getReference("UsersCart/$value")
            var dishid = ref.push().key
            var user = viewSpecialDIshModel(dishcost,dishname,value,restname)
            ref.child(dishid!!).setValue(user).addOnCompleteListener {
                Toast.makeText(this@searched_dish, "Item added to cart goto->home->Mycart", Toast.LENGTH_LONG).show()
                this@searched_dish.finish()
            }
        })

    }
}
