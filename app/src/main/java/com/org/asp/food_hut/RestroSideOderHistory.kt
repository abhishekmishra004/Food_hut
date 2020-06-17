package com.org.asp.food_hut

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class RestroSideOderHistory : AppCompatActivity() {

    var mRecyclerView: RecyclerView?=null
    var mfirebasedatabase = FirebaseDatabase.getInstance()
    var pos = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restro_side_oder_history)
        mRecyclerView = findViewById(R.id.RestHistRecy)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        val mIntent = intent
        pos = mIntent.getStringExtra("pos")
        Toast.makeText(this@RestroSideOderHistory,pos,Toast.LENGTH_SHORT).show()
        if (pos == "1") {
            var mRef = mfirebasedatabase!!.getReference().child("RestroOderHistory/Adda Unpluged")
            save(mRef)

        } else if (pos == "2") {
            var mRef = mfirebasedatabase!!.getReference().child("RestroOderHistory/Hideaway Restro & Cafe")
            save(mRef)

        } else if (pos == "3") {
            var mRef = mfirebasedatabase!!.getReference().child("RestroOderHistory/Michael's Kitchen")
            save(mRef)

        } else if (pos == "4") {
            var mRef = mfirebasedatabase!!.getReference().child("RestroOderHistory/Paris Bakery")
            save(mRef)

        } else if (pos == "5") {
            var mRef = mfirebasedatabase!!.getReference().child("RestroOderHistory/The Dugout")
            save(mRef)

        } else if (pos == "6") {
            var mRef = mfirebasedatabase!!.getReference().child("RestroOderHistory/The Restaurant")
            save(mRef)

        } else if (pos == "7") {
            var mRef = mfirebasedatabase!!.getReference().child("RestroOderHistory/Waffle House")
            save(mRef)

        }

    }

    fun save(ds: DatabaseReference)
    {
        var mAdapter = object : FirebaseRecyclerAdapter<RestroSideDeliverdModel, restside_history_Viewholder>(
                RestroSideDeliverdModel::class.java,R.layout.restside_histrory_image,restside_history_Viewholder::class.java,ds) {
            override fun populateViewHolder(viewHolder: restside_history_Viewholder?, model: RestroSideDeliverdModel?, position: Int) {
                Toast.makeText(this@RestroSideOderHistory,"test "+model,Toast.LENGTH_SHORT).show()
                viewHolder?.setdetails(model?.address!!,model?.custno!!,model?.username,model?.restname!!,model?.odate!!,model?.dcost!!,model?.dname!!,model?.transid!!)
            }

        }
        mRecyclerView?.adapter = mAdapter

    }
}
