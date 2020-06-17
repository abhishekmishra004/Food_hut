package com.org.asp.food_hut

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*

class CurrentOder : AppCompatActivity() {

    var mRecyclerView: RecyclerView? = null
    var mfirebasedatabase: FirebaseDatabase? = null
    var mRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_oder)
        val mIntent = intent
        val phoneNO: String = mIntent.getStringExtra("phoneNO")
        mRecyclerView = findViewById(R.id.currentoderrecy)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mRef = FirebaseDatabase.getInstance().getReference("CurrentOder").child(phoneNO)
    }


    override fun onStart() {

        var mAdapter = object : FirebaseRecyclerAdapter<history_model, CurrentViewholder>(
                history_model::class.java, R.layout.current_image, CurrentViewholder::class.java, mRef) {
            override fun populateViewHolder(viewHolder: CurrentViewholder?, model: history_model?, position: Int) {
                viewHolder?.setdetails(model?.date!!,model?.dishcost!!,model?.dishname!!,model?.restroname!!,model?.deliveryStatus!!)
            }


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentViewholder {
                var viewholder: CurrentViewholder = super.onCreateViewHolder(parent, viewType)
                viewholder.setOnClickListner(object : CurrentViewholder.Clicklistener {
                    override fun onItemclick(view: View, pos: Int) {
                        // Toast.makeText(this@CurrentOder, getItem(pos).dishcost, Toast.LENGTH_SHORT).show()
                        var naming = getItem(pos).dishname
                        var rname  = getItem(pos).restroname
                        val build = AlertDialog.Builder(this@CurrentOder)
                        build.setMessage("Are you sure want to remove this item?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, id: Int) {
                                        var mquery: Query = mRef?.orderByChild("dishname")?.equalTo(naming)!!
                                        mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                            override fun onCancelled(p0: DatabaseError) {
                                                print("Some Error happened!!")
                                            }

                                            override fun onDataChange(p0: DataSnapshot) {
                                                for (ds in p0.children) {
                                                    ds.ref.removeValue()
                                                }
                                                Toast.makeText(this@CurrentOder, "Item Removed", Toast.LENGTH_SHORT).show()
                                            }
                                        })
                                        var query: Query = FirebaseDatabase.getInstance().getReference("9853732536/"+rname).orderByChild("dname")?.equalTo(naming)
                                        query.addListenerForSingleValueEvent(object : ValueEventListener{
                                            override fun onCancelled(p0: DatabaseError) {

                                            }

                                            override fun onDataChange(p0: DataSnapshot) {
                                                for (ds in p0.children) {
                                                    ds.ref.removeValue()
                                                }
                                            }
                                        })
                                    }
                                })
                                .setNegativeButton("No", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface, id: Int) {
                                        dialog.cancel()
                                    }
                                })
                        var alert = build.create()
                        alert.show()
                    }
                })
                return viewholder

            }

        }
        mRecyclerView?.adapter = mAdapter



        super.onStart()
    }
}
