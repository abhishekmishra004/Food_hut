package com.org.asp.food_hut

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telephony.SmsManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import com.org.asp.food_hut.R
import kotlinx.android.synthetic.main.fragment_cart.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class Cart : AppCompatActivity() {

    var mRecyclerView: RecyclerView? = null
    var mfirebasedatabase: FirebaseDatabase? = null
    var mRef: DatabaseReference? = null
    var mAdapter: FirebaseRecyclerAdapter<viewSpecialDIshModel, Viewholder3>? = null
    var total: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_cart)
        var bool: Boolean? = null
        var checking: Boolean? = null
        var actionbarr: ActionBar? = this!!.supportActionBar
        actionbarr?.title = "Cart"
        mRecyclerView = findViewById(R.id.cart_item_recy)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mfirebasedatabase = FirebaseDatabase.getInstance()
        val mIntent = intent
        val phoneNO: String = mIntent.getStringExtra("phoneNO")
        Toast.makeText(this,phoneNO,Toast.LENGTH_SHORT).show()

        mRef = mfirebasedatabase!!.getReference().child("UsersCart").child(phoneNO)
        var test = mRef?.key
        if (test == null) {
            Toast.makeText(this@Cart, "Cart is Empty !!", Toast.LENGTH_SHORT).show()
            this@Cart.finish()
        } else {
          //  Toast.makeText(this, "test=" + test, Toast.LENGTH_SHORT).show()
            mAdapter = object : FirebaseRecyclerAdapter<viewSpecialDIshModel, Viewholder3>(
                    viewSpecialDIshModel::class.java, R.layout.cart_image, Viewholder3::class.java, mRef) {
                override fun populateViewHolder(viewHolder: Viewholder3?, model: viewSpecialDIshModel?, position: Int) {
                    total = total + model?.dishcost?.toInt()!!
                    viewHolder?.setdetails(this@Cart, model?.dishcost!!, model?.dishname!!, model?.phoneno!!, model?.Restroname!!)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder3 {
                    var viewholder: Viewholder3 = super.onCreateViewHolder(parent, viewType)
                    viewholder.setOnClickListner(object : Viewholder3.Clicklistener {
                        override fun onItemclick(view: View, pos: Int) {
                            Toast.makeText(this@Cart, getItem(pos).dishcost, Toast.LENGTH_SHORT).show()
                            var naming = getItem(pos).dishname

                            val build = AlertDialog.Builder(this@Cart)
                            build.setMessage("Are you sure want to remove this item?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, id: Int) {
                                            total = total - getItem(pos).dishcost!!.toInt()
                                            var mquery: Query = mRef?.orderByChild("dishname")?.equalTo(naming)!!
                                            mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError) {
                                                    print("Some Error happened!!")
                                                }

                                                override fun onDataChange(p0: DataSnapshot) {
                                                    for (ds in p0.children) {
                                                        ds.ref.removeValue()
                                                    }


                                                    Toast.makeText(this@Cart, "Item Removed", Toast.LENGTH_SHORT).show()
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
        }


        Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show()

        button_buy_now?.setOnClickListener {
            var add: EditText = findViewById(R.id.addressedit)
            var addres = add.text.toString()
            if(TextUtils.isEmpty(addres))
            {
                Toast.makeText(this@Cart,"Enter address:",Toast.LENGTH_SHORT).show()
            }
            else {
                val builder = AlertDialog.Builder(this@Cart)
                builder.setMessage(" Are you sure you want to Buy? \n Toatal cost is " + total)
                        .setCancelable(false)
                        .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, id: Int) {
                                var oderid = FirebaseDatabase.getInstance().getReference("UsersCart").child(phoneNO).key.toString()
                                var query: Query = FirebaseDatabase.getInstance().getReference("UsersCart").child(phoneNO)
                                query.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        for (ds in p0.children) {
                                            Toast.makeText(this@Cart,phoneNO,Toast.LENGTH_LONG).show()
                                            val ref = FirebaseDatabase.getInstance().getReference("CurrentOder/$phoneNO")
                                            val today = org.joda.time.LocalDate()
                                            var ts = today.toString()
                                            var oderid = ref.push().key
                                            //Toast.makeText(this@Cart,p0.toString(),Toast.LENGTH_LONG).show()
                                            var obj = history_model(ts, ds.child("dishcost").value.toString(), ds.child("dishname").value.toString(),ds.child("restroname").value.toString(),"Waiting","null")
                                            ref.child(oderid!!).setValue(obj)
                                            val smsManager = SmsManager.getDefault()
                                            smsManager.sendTextMessage("8249286911", null, "New order Details: \norder id- $oderid\nRestaurant name "+ ds.child("restroname").value.toString()
                                                    + "\nDishname-"+ds.child("dishname").value.toString()+"\nDishcost:"+ds.child("dishcost").value.toString() +"\nFor more detail Open App", null, null)
                                            var restname =ds.child("restroname").value.toString()
                                            var usertexts = String()
                                            val rootRef = FirebaseDatabase.getInstance().reference
                                            val checkUser = rootRef.child("UserLoginInfo/$phoneNO").ref
                                            val postListener = object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError) {
                                                }

                                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                                    val post = dataSnapshot.getValue(UserLoginInfo::class.java)

                                                    usertexts=post?.getUsername()!!
                                                    var dcost =  ds.child("dishcost").value.toString()
                                                    var dname =ds.child("dishname").value.toString()
                                                    val restoref = FirebaseDatabase.getInstance().getReference("9853732536/"+restname)
                                                    var reso = RestroUserListModel(addres,phoneNO,usertexts!!,restname,ts,dcost,dname)
                                                    restoref.child(oderid).setValue(reso)

                                                }

                                            }
                                            checkUser.addListenerForSingleValueEvent(postListener)



                                        }
                                    }

                                })
                                var delref = FirebaseDatabase.getInstance().getReference("UsersCart").child(phoneNO)
                                delref.setValue(null)
                                Toast.makeText(this@Cart, "Order is placed shortly you will get a call from the Restaurant for confirming the Order", Toast.LENGTH_LONG).show()
                                this@Cart.finish()
                            }
                        })
                        .setNegativeButton("No", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, id: Int) {
                                dialog.cancel()
                            }
                        })
                val alert = builder.create()
                alert.show()
            }
        }

    }


}
