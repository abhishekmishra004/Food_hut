package com.org.asp.food_hut

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class oders : AppCompatActivity() {

    var mRecyclerView: RecyclerView?=null
    var mfirebasedatabase: FirebaseDatabase? = null
    var mRef: DatabaseReference? = null
    var mAdapter: FirebaseRecyclerAdapter<history_model, History_Viewholder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oders)
        var actionbarr : ActionBar? = this!!.supportActionBar
        actionbarr?.title = "Order History"
        mRecyclerView = findViewById(R.id.recy_history)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        val mIntent = intent
        val phoneNO: String = mIntent.getStringExtra("phoneNO")
        Toast.makeText(this,phoneNO,Toast.LENGTH_SHORT).show()
        mfirebasedatabase = FirebaseDatabase.getInstance()
        mRef = mfirebasedatabase!!.getReference("OderHistory").child(phoneNO)
        Toast.makeText(this@oders," key" +mRef?.key,Toast.LENGTH_SHORT).show()
        mAdapter = object : FirebaseRecyclerAdapter<history_model,History_Viewholder>(
                history_model::class.java, R.layout.history, History_Viewholder::class.java, mRef) {
            override fun populateViewHolder(viewHolder: History_Viewholder?, model: history_model?, position: Int) {
                viewHolder?.setdetails(model?.date!!,model?.dishcost!!,model?.dishname!!,model?.restroname!!,model?.deliveryStatus!!,model?.transid!!)
            }

        }
        mRecyclerView?.adapter = mAdapter
        Toast.makeText(this,"oder", Toast.LENGTH_SHORT).show()
    }
}
