package com.org.asp.food_hut

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class Restro_dishes : AppCompatActivity() {

    var posRecy: String? = null
    var textResto: TextView? = null
    var imageRestro: ImageView? = null
    val rootRef = FirebaseDatabase.getInstance().reference
    var mRecyclerView: RecyclerView? = null
    var mfirebasedatabase: FirebaseDatabase? = null
    var mRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restro_dishes)
        var actionbarr : ActionBar? = this!!.supportActionBar
        actionbarr?.title = "Dishes"
        textResto = findViewById(R.id.TextRestroText)
        imageRestro = findViewById(R.id.restroImageDish)
        mRecyclerView = findViewById(R.id.RecyclerDishes)
        var intent = this.getIntent()
        var bundle = intent.getBundleExtra("pos")
        posRecy = bundle.getString("position", "")
        var phone = bundle.getString("val")
        Toast.makeText(this@Restro_dishes,phone,Toast.LENGTH_SHORT).show()
        Toast.makeText(this@Restro_dishes,posRecy,Toast.LENGTH_SHORT).show()
        val restroImage = rootRef.child("RestroImage/$posRecy")
        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                var name = p0.child("Restro_name")
                var photo = p0.child("Url")

                textResto?.text = name.value as String
                var loading: String = photo.value as String

                Picasso.get().load(loading).into(imageRestro)

            }

        }
        restroImage.addListenerForSingleValueEvent(postListener)

        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mfirebasedatabase = FirebaseDatabase.getInstance()
        mRef = mfirebasedatabase!!.getReference("Dishes/$posRecy")

        var mAdapter = object : FirebaseRecyclerAdapter<Model2, Viewholder2>
        (Model2::class.java, R.layout.dish_image, Viewholder2::class.java, mRef) {
            override fun populateViewHolder(viewHolder: Viewholder2?, model: Model2?, position: Int) {
                viewHolder!!.setdetails(this@Restro_dishes, model?.getCost()!!, model?.getDetails()!!, model?.getName()!!, model?.geturl()!!)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder2 {
                var viewholderr: Viewholder2 = super.onCreateViewHolder(parent, viewType)
                viewholderr.setOnClickListner(object : Viewholder2.Clicklistener {
                    override fun onItemclick(view: View, pos: Int) {
                        val intentt = Intent(this@Restro_dishes, view_dish::class.java)
                        var dishimage:String = getItem(pos).geturl()!!
                        var name:String = getItem(pos).RestroName!!
                        var bundlee = viewholderr.getDetails()
                        bundlee?.putString("url",dishimage)
                        bundlee?.putString("val",phone)
                        bundlee?.putString("restname",name)
                        intentt.putExtra("dish", bundlee)
                        startActivity(intentt)

                    }

                })

                return viewholderr
            }
        }
        mRecyclerView?.adapter = mAdapter

    }


}
