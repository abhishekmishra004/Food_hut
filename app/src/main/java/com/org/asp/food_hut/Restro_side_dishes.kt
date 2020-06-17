package com.org.asp.food_hut

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telephony.SmsManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.restro_side_dishes_activity.*

class Restro_side_dishes : AppCompatActivity() {

    var mRecyclerView: RecyclerView? = null
    var mAdapter: FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>? = null
    var pos = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restro_side_dishes_activity)
        val mIntent = intent
        mRecyclerView = findViewById(R.id.restSideRecy)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        pos = mIntent.getStringExtra("pos")
        Toast.makeText(this@Restro_side_dishes, pos, Toast.LENGTH_LONG).show()
        if (pos == "1") {
            var mRef = FirebaseDatabase.getInstance().getReference("9853732536/Adda Unpluged")
            Toast.makeText(this@Restro_side_dishes, mRef.toString(), Toast.LENGTH_LONG).show()
            mAdapter = object : FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>(
                    RestroUserListModel::class.java, R.layout.oder_list_image, RestroUserListViewholder::class.java, mRef) {
                override fun populateViewHolder(viewHolder: RestroUserListViewholder?, model: RestroUserListModel?, position: Int) {
                    viewHolder?.setdetails(model?.address!!, model?.custno!!, model?.username!!, model?.restname!!, model?.odate, model?.dcost!!, model?.dname!!)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestroUserListViewholder {
                    var viewholder: RestroUserListViewholder = super.onCreateViewHolder(parent, viewType)
                    viewholder.setOnClickListner(object : RestroUserListViewholder.Clicklistener {
                        override fun onItemclick(view: View, pos: Int) {
                            var naming = getItem(pos).dname
                            var custno = getItem(pos).custno
                            var mDishcost = getItem(pos).dcost
                            var mDate = getItem(pos).odate
                            var mrestname = getItem(pos).restname
                            val build = AlertDialog.Builder(this@Restro_side_dishes)
                            build.setMessage("Are you sure want to remove this item?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, id: Int) {
                                            var mquery: Query = mRef?.orderByChild("dname")?.equalTo(naming)!!
                                            mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError) {
                                                    print("Some Error happened!!")
                                                }

                                                override fun onDataChange(p0: DataSnapshot) {
                                                    for (ds in p0.children) {
                                                        ds.ref.removeValue()
                                                    }
                                                    var Ref = FirebaseDatabase.getInstance().getReference("CurrentOder/$custno")
                                                    var query: Query = Ref?.orderByChild("dishname")?.equalTo(naming)!!
                                                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                                                        override fun onCancelled(p0: DatabaseError) {

                                                        }

                                                        override fun onDataChange(p0: DataSnapshot) {
                                                            for (ds in p0.children) {
                                                                ds.ref.removeValue()
                                                            }
                                                        }
                                                    })

                                                    //

                                                    val ref = FirebaseDatabase.getInstance().getReference("OderHistory/$custno")
                                                    val user = history_model(mDate, mDishcost, naming, mrestname, "Failed","null")
                                                    var o = ref.push().key
                                                    ref.child(o!!).setValue(user)
                                                    Toast.makeText(this@Restro_side_dishes, "Item Removed", Toast.LENGTH_SHORT).show()
                                                    val smsManager = SmsManager.getDefault()
                                                    smsManager.sendTextMessage(custno, null, "THe specific order hsa been canceled from restaurant : Restaurant name "+ mrestname
                                                            + "\nDishname-"+naming+"\nDishcost:"+mDishcost+"\n For more detail Open App", null, null)
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

            button_confirm.setOnClickListener({
                var que: Query = FirebaseDatabase.getInstance().getReference("9853732536/Adda Unpluged")
                que.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (ds in p0.children) {
                            val ref = FirebaseDatabase.getInstance().getReference("RestroOder/Adda Unpluged")
                            var obj = RestroUserListModel(ds.child("address").value.toString(), ds.child("custno").value.toString(), ds.child("username").value.toString()
                                    , ds.child("restname").value.toString(), ds.child("odate").value.toString(), ds.child("dcost").value.toString(), ds.child("dname").value.toString())
                            var oderid = ref.push().key
                            ref.child(oderid!!).setValue(obj)
                            val smsManager = SmsManager.getDefault()
                            var no =ds.child("custno").value.toString()
                            smsManager.sendTextMessage(no , null, "THe specific order hsa been CONFIRMED from restaurant : Restaurant name "+ ds.child("restname").value.toString()
                                    + "\nDishname-"+ds.child("dname").value.toString()+"\nDishcost:"+ ds.child("dcost").value.toString()+"\n For more detail Open App", null, null)
                        }
                    }

                })
                var delref = FirebaseDatabase.getInstance().getReference("9853732536/Adda Unpluged")
                delref.setValue(null)

            })
        } else if (pos == "2") {
            var mRef = FirebaseDatabase.getInstance().getReference("9853732536/Hideaway Restro & Cafe")
            Toast.makeText(this@Restro_side_dishes, mRef.toString(), Toast.LENGTH_LONG).show()
            mAdapter = object : FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>(
                    RestroUserListModel::class.java, R.layout.oder_list_image, RestroUserListViewholder::class.java, mRef) {
                override fun populateViewHolder(viewHolder: RestroUserListViewholder?, model: RestroUserListModel?, position: Int) {
                    viewHolder?.setdetails(model?.address!!, model?.custno!!, model?.username!!, model?.restname!!, model?.odate, model?.dcost!!, model?.dname!!)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestroUserListViewholder {
                    var viewholder: RestroUserListViewholder = super.onCreateViewHolder(parent, viewType)
                    viewholder.setOnClickListner(object : RestroUserListViewholder.Clicklistener {
                        override fun onItemclick(view: View, pos: Int) {
                            var naming = getItem(pos).dname
                            var custno = getItem(pos).custno
                            var mDishcost = getItem(pos).dcost
                            var mDate = getItem(pos).odate
                            var mrestname = getItem(pos).restname
                            val build = AlertDialog.Builder(this@Restro_side_dishes)
                            build.setMessage("Are you sure want to remove this item?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, id: Int) {
                                            var mquery: Query = mRef?.orderByChild("dname")?.equalTo(naming)!!
                                            mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError) {
                                                    print("Some Error happened!!")
                                                }

                                                override fun onDataChange(p0: DataSnapshot) {
                                                    for (ds in p0.children) {
                                                        ds.ref.removeValue()
                                                    }
                                                    var Ref = FirebaseDatabase.getInstance().getReference("CurrentOder/$custno")
                                                    var query: Query = Ref?.orderByChild("dishname")?.equalTo(naming)!!
                                                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                                                        override fun onCancelled(p0: DatabaseError) {

                                                        }

                                                        override fun onDataChange(p0: DataSnapshot) {
                                                            for (ds in p0.children) {
                                                                ds.ref.removeValue()
                                                            }
                                                        }
                                                    })

                                                    //

                                                    val ref = FirebaseDatabase.getInstance().getReference("OderHistory/$custno")
                                                    val user = history_model(mDate, mDishcost, naming, mrestname, "Failed","null")
                                                    var o = ref.push().key
                                                    ref.child(o!!).setValue(user)
                                                    Toast.makeText(this@Restro_side_dishes, "Item Removed", Toast.LENGTH_SHORT).show()
                                                    val smsManager = SmsManager.getDefault()
                                                    smsManager.sendTextMessage(custno, null, "THe specific order hsa been canceled from restaurant : Restaurant name "+ mrestname
                                                            + "\nDishname-"+naming+"\nDishcost:"+mDishcost+"\n For more detail Open App", null, null)
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

            button_confirm.setOnClickListener({
                var que: Query = FirebaseDatabase.getInstance().getReference("9853732536/Hideaway Restro & Cafe")
                que.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (ds in p0.children) {
                            val ref = FirebaseDatabase.getInstance().getReference("RestroOder/Hideaway Restro & Cafe")
                            var obj = RestroUserListModel(ds.child("address").value.toString(), ds.child("custno").value.toString(), ds.child("username").value.toString()
                                    , ds.child("restname").value.toString(), ds.child("odate").value.toString(), ds.child("dcost").value.toString(), ds.child("dname").value.toString())
                            var oderid = ref.push().key
                            ref.child(oderid!!).setValue(obj)
                        }
                    }

                })
                var delref = FirebaseDatabase.getInstance().getReference("9853732536/Hideaway Restro & Cafe")
                delref.setValue(null)

            })
        } else if (pos == "3") {
            var mRef = FirebaseDatabase.getInstance().getReference("9853732536/Michael's Kitchen")
            Toast.makeText(this@Restro_side_dishes, mRef.toString(), Toast.LENGTH_LONG).show()
            mAdapter = object : FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>(
                    RestroUserListModel::class.java, R.layout.oder_list_image, RestroUserListViewholder::class.java, mRef) {
                override fun populateViewHolder(viewHolder: RestroUserListViewholder?, model: RestroUserListModel?, position: Int) {
                    viewHolder?.setdetails(model?.address!!, model?.custno!!, model?.username!!, model?.restname!!, model?.odate, model?.dcost!!, model?.dname!!)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestroUserListViewholder {
                    var viewholder: RestroUserListViewholder = super.onCreateViewHolder(parent, viewType)
                    viewholder.setOnClickListner(object : RestroUserListViewholder.Clicklistener {
                        override fun onItemclick(view: View, pos: Int) {
                            var naming = getItem(pos).dname
                            var custno = getItem(pos).custno
                            var mDishcost = getItem(pos).dcost
                            var mDate = getItem(pos).odate
                            var mrestname = getItem(pos).restname
                            val build = AlertDialog.Builder(this@Restro_side_dishes)
                            build.setMessage("Are you sure want to remove this item?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, id: Int) {
                                            var mquery: Query = mRef?.orderByChild("dname")?.equalTo(naming)!!
                                            mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError) {
                                                    print("Some Error happened!!")
                                                }

                                                override fun onDataChange(p0: DataSnapshot) {
                                                    for (ds in p0.children) {
                                                        ds.ref.removeValue()
                                                    }
                                                    var Ref = FirebaseDatabase.getInstance().getReference("CurrentOder/$custno")
                                                    var query: Query = Ref?.orderByChild("dishname")?.equalTo(naming)!!
                                                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                                                        override fun onCancelled(p0: DatabaseError) {

                                                        }

                                                        override fun onDataChange(p0: DataSnapshot) {
                                                            for (ds in p0.children) {
                                                                ds.ref.removeValue()
                                                            }
                                                        }
                                                    })

                                                    //

                                                    val ref = FirebaseDatabase.getInstance().getReference("OderHistory/$custno")
                                                    val user = history_model(mDate, mDishcost, naming, mrestname, "Failed","null")
                                                    var o = ref.push().key
                                                    ref.child(o!!).setValue(user)
                                                    Toast.makeText(this@Restro_side_dishes, "Item Removed", Toast.LENGTH_SHORT).show()
                                                    val smsManager = SmsManager.getDefault()
                                                    smsManager.sendTextMessage(custno, null, "THe specific order hsa been canceled from restaurant : Restaurant name "+ mrestname
                                                            + "\nDishname-"+naming+"\nDishcost:"+mDishcost+"\n For more detail Open App", null, null)
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

            button_confirm.setOnClickListener({
                var que: Query = FirebaseDatabase.getInstance().getReference("9853732536/Michael's Kitchen")
                que.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (ds in p0.children) {
                            val ref = FirebaseDatabase.getInstance().getReference("RestroOder/Michael's Kitchen")
                            var obj = RestroUserListModel(ds.child("address").value.toString(), ds.child("custno").value.toString(), ds.child("username").value.toString()
                                    , ds.child("restname").value.toString(), ds.child("odate").value.toString(), ds.child("dcost").value.toString(), ds.child("dname").value.toString())
                            var oderid = ref.push().key
                            ref.child(oderid!!).setValue(obj)
                        }
                    }

                })
                var delref = FirebaseDatabase.getInstance().getReference("9853732536/Michael's Kitchen")
                delref.setValue(null)

            })
        } else if (pos == "4") {
            var mRef = FirebaseDatabase.getInstance().getReference("9853732536/Paris Bakery")
            Toast.makeText(this@Restro_side_dishes, mRef.toString(), Toast.LENGTH_LONG).show()
            mAdapter = object : FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>(
                    RestroUserListModel::class.java, R.layout.oder_list_image, RestroUserListViewholder::class.java, mRef) {
                override fun populateViewHolder(viewHolder: RestroUserListViewholder?, model: RestroUserListModel?, position: Int) {
                    viewHolder?.setdetails(model?.address!!, model?.custno!!, model?.username!!, model?.restname!!, model?.odate, model?.dcost!!, model?.dname!!)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestroUserListViewholder {
                    var viewholder: RestroUserListViewholder = super.onCreateViewHolder(parent, viewType)
                    viewholder.setOnClickListner(object : RestroUserListViewholder.Clicklistener {
                        override fun onItemclick(view: View, pos: Int) {
                            var naming = getItem(pos).dname
                            var custno = getItem(pos).custno
                            var mDishcost = getItem(pos).dcost
                            var mDate = getItem(pos).odate
                            var mrestname = getItem(pos).restname
                            val build = AlertDialog.Builder(this@Restro_side_dishes)
                            build.setMessage("Are you sure want to remove this item?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, id: Int) {
                                            var mquery: Query = mRef?.orderByChild("dname")?.equalTo(naming)!!
                                            mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError) {
                                                    print("Some Error happened!!")
                                                }

                                                override fun onDataChange(p0: DataSnapshot) {
                                                    for (ds in p0.children) {
                                                        ds.ref.removeValue()
                                                    }
                                                    var Ref = FirebaseDatabase.getInstance().getReference("CurrentOder/$custno")
                                                    var query: Query = Ref?.orderByChild("dishname")?.equalTo(naming)!!
                                                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                                                        override fun onCancelled(p0: DatabaseError) {

                                                        }

                                                        override fun onDataChange(p0: DataSnapshot) {
                                                            for (ds in p0.children) {
                                                                ds.ref.removeValue()
                                                            }
                                                        }
                                                    })

                                                    //

                                                    val ref = FirebaseDatabase.getInstance().getReference("OderHistory/$custno")
                                                    val user = history_model(mDate, mDishcost, naming, mrestname, "Failed","null")
                                                    var o = ref.push().key
                                                    ref.child(o!!).setValue(user)
                                                    Toast.makeText(this@Restro_side_dishes, "Item Removed", Toast.LENGTH_SHORT).show()
                                                    val smsManager = SmsManager.getDefault()
                                                    smsManager.sendTextMessage(custno, null, "THe specific order hsa been canceled from restaurant : Restaurant name "+ mrestname
                                                            + "\nDishname-"+naming+"\nDishcost:"+mDishcost+"\n For more detail Open App", null, null)
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

            button_confirm.setOnClickListener({
                var que: Query = FirebaseDatabase.getInstance().getReference("9853732536/Paris Bakery")
                que.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (ds in p0.children) {
                            val ref = FirebaseDatabase.getInstance().getReference("RestroOder/Paris Bakery")
                            var obj = RestroUserListModel(ds.child("address").value.toString(), ds.child("custno").value.toString(), ds.child("username").value.toString()
                                    , ds.child("restname").value.toString(), ds.child("odate").value.toString(), ds.child("dcost").value.toString(), ds.child("dname").value.toString())
                            var oderid = ref.push().key
                            ref.child(oderid!!).setValue(obj)
                        }
                    }

                })
                var delref = FirebaseDatabase.getInstance().getReference("9853732536/Paris Bakery")
                delref.setValue(null)

            })
        } else if (pos == "5") {
            var mRef = FirebaseDatabase.getInstance().getReference("9853732536/The Dugout")
            Toast.makeText(this@Restro_side_dishes, mRef.toString(), Toast.LENGTH_LONG).show()
            mAdapter = object : FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>(
                    RestroUserListModel::class.java, R.layout.oder_list_image, RestroUserListViewholder::class.java, mRef) {
                override fun populateViewHolder(viewHolder: RestroUserListViewholder?, model: RestroUserListModel?, position: Int) {
                    viewHolder?.setdetails(model?.address!!, model?.custno!!, model?.username!!, model?.restname!!, model?.odate, model?.dcost!!, model?.dname!!)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestroUserListViewholder {
                    var viewholder: RestroUserListViewholder = super.onCreateViewHolder(parent, viewType)
                    viewholder.setOnClickListner(object : RestroUserListViewholder.Clicklistener {
                        override fun onItemclick(view: View, pos: Int) {
                            var naming = getItem(pos).dname
                            var custno = getItem(pos).custno
                            var mDishcost = getItem(pos).dcost
                            var mDate = getItem(pos).odate
                            var mrestname = getItem(pos).restname
                            val build = AlertDialog.Builder(this@Restro_side_dishes)
                            build.setMessage("Are you sure want to remove this item?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, id: Int) {
                                            var mquery: Query = mRef?.orderByChild("dname")?.equalTo(naming)!!
                                            mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError) {
                                                    print("Some Error happened!!")
                                                }

                                                override fun onDataChange(p0: DataSnapshot) {
                                                    for (ds in p0.children) {
                                                        ds.ref.removeValue()
                                                    }
                                                    var Ref = FirebaseDatabase.getInstance().getReference("CurrentOder/$custno")
                                                    var query: Query = Ref?.orderByChild("dishname")?.equalTo(naming)!!
                                                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                                                        override fun onCancelled(p0: DatabaseError) {

                                                        }

                                                        override fun onDataChange(p0: DataSnapshot) {
                                                            for (ds in p0.children) {
                                                                ds.ref.removeValue()
                                                            }
                                                        }
                                                    })

                                                    //

                                                    val ref = FirebaseDatabase.getInstance().getReference("OderHistory/$custno")
                                                    val user = history_model(mDate, mDishcost, naming, mrestname, "Failed","null")
                                                    var o = ref.push().key
                                                    ref.child(o!!).setValue(user)
                                                    Toast.makeText(this@Restro_side_dishes, "Item Removed", Toast.LENGTH_SHORT).show()
                                                    val smsManager = SmsManager.getDefault()
                                                    smsManager.sendTextMessage(custno, null, "THe specific order hsa been canceled from restaurant : Restaurant name "+ mrestname
                                                            + "\nDishname-"+naming+"\nDishcost:"+mDishcost+"\n For more detail Open App", null, null)
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

            button_confirm.setOnClickListener({
                var que: Query = FirebaseDatabase.getInstance().getReference("9853732536/The Dugout")
                que.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (ds in p0.children) {
                            val ref = FirebaseDatabase.getInstance().getReference("RestroOder/The Dugout")
                            var obj = RestroUserListModel(ds.child("address").value.toString(), ds.child("custno").value.toString(), ds.child("username").value.toString()
                                    , ds.child("restname").value.toString(), ds.child("odate").value.toString(), ds.child("dcost").value.toString(), ds.child("dname").value.toString())
                            var oderid = ref.push().key
                            ref.child(oderid!!).setValue(obj)
                        }
                    }

                })
                var delref = FirebaseDatabase.getInstance().getReference("9853732536/The Dugout")
                delref.setValue(null)

            })
        } else if (pos == "6") {
            var mRef = FirebaseDatabase.getInstance().getReference("9853732536/The Restaurant")
            Toast.makeText(this@Restro_side_dishes, mRef.toString(), Toast.LENGTH_LONG).show()
            mAdapter = object : FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>(
                    RestroUserListModel::class.java, R.layout.oder_list_image, RestroUserListViewholder::class.java, mRef) {
                override fun populateViewHolder(viewHolder: RestroUserListViewholder?, model: RestroUserListModel?, position: Int) {
                    viewHolder?.setdetails(model?.address!!, model?.custno!!, model?.username!!, model?.restname!!, model?.odate, model?.dcost!!, model?.dname!!)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestroUserListViewholder {
                    var viewholder: RestroUserListViewholder = super.onCreateViewHolder(parent, viewType)
                    viewholder.setOnClickListner(object : RestroUserListViewholder.Clicklistener {
                        override fun onItemclick(view: View, pos: Int) {
                            var naming = getItem(pos).dname
                            var custno = getItem(pos).custno
                            var mDishcost = getItem(pos).dcost
                            var mDate = getItem(pos).odate
                            var mrestname = getItem(pos).restname
                            val build = AlertDialog.Builder(this@Restro_side_dishes)
                            build.setMessage("Are you sure want to remove this item?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, id: Int) {
                                            var mquery: Query = mRef?.orderByChild("dname")?.equalTo(naming)!!
                                            mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError) {
                                                    print("Some Error happened!!")
                                                }

                                                override fun onDataChange(p0: DataSnapshot) {
                                                    for (ds in p0.children) {
                                                        ds.ref.removeValue()
                                                    }
                                                    var Ref = FirebaseDatabase.getInstance().getReference("CurrentOder/$custno")
                                                    var query: Query = Ref?.orderByChild("dishname")?.equalTo(naming)!!
                                                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                                                        override fun onCancelled(p0: DatabaseError) {

                                                        }

                                                        override fun onDataChange(p0: DataSnapshot) {
                                                            for (ds in p0.children) {
                                                                ds.ref.removeValue()
                                                            }
                                                        }
                                                    })

                                                    //

                                                    val ref = FirebaseDatabase.getInstance().getReference("OderHistory/$custno")
                                                    val user = history_model(mDate, mDishcost, naming, mrestname, "Failed","null")
                                                    var o = ref.push().key
                                                    ref.child(o!!).setValue(user)
                                                    Toast.makeText(this@Restro_side_dishes, "Item Removed", Toast.LENGTH_SHORT).show()
                                                    val smsManager = SmsManager.getDefault()
                                                    smsManager.sendTextMessage(custno, null, "THe specific order hsa been canceled from restaurant : Restaurant name "+ mrestname
                                                            + "\nDishname-"+naming+"\nDishcost:"+mDishcost+"\n For more detail Open App", null, null)
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

            button_confirm.setOnClickListener({
                var que: Query = FirebaseDatabase.getInstance().getReference("9853732536/The Restaurant")
                que.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (ds in p0.children) {
                            val ref = FirebaseDatabase.getInstance().getReference("RestroOder/The Restaurant")
                            var obj = RestroUserListModel(ds.child("address").value.toString(), ds.child("custno").value.toString(), ds.child("username").value.toString()
                                    , ds.child("restname").value.toString(), ds.child("odate").value.toString(), ds.child("dcost").value.toString(), ds.child("dname").value.toString())
                            var oderid = ref.push().key
                            ref.child(oderid!!).setValue(obj)
                        }
                    }

                })
                var delref = FirebaseDatabase.getInstance().getReference("9853732536/The Restaurant")
                delref.setValue(null)

            })
        } else if (pos == "7") {
            var mRef = FirebaseDatabase.getInstance().getReference("9853732536/Waffle House")
            Toast.makeText(this@Restro_side_dishes, mRef.toString(), Toast.LENGTH_LONG).show()
            mAdapter = object : FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>(
                    RestroUserListModel::class.java, R.layout.oder_list_image, RestroUserListViewholder::class.java, mRef) {
                override fun populateViewHolder(viewHolder: RestroUserListViewholder?, model: RestroUserListModel?, position: Int) {
                    viewHolder?.setdetails(model?.address!!, model?.custno!!, model?.username!!, model?.restname!!, model?.odate, model?.dcost!!, model?.dname!!)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestroUserListViewholder {
                    var viewholder: RestroUserListViewholder = super.onCreateViewHolder(parent, viewType)
                    viewholder.setOnClickListner(object : RestroUserListViewholder.Clicklistener {
                        override fun onItemclick(view: View, pos: Int) {
                            var naming = getItem(pos).dname
                            var custno = getItem(pos).custno
                            var mDishcost = getItem(pos).dcost
                            var mDate = getItem(pos).odate
                            var mrestname = getItem(pos).restname
                            val build = AlertDialog.Builder(this@Restro_side_dishes)
                            build.setMessage("Are you sure want to remove this item?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, id: Int) {
                                            var mquery: Query = mRef?.orderByChild("dname")?.equalTo(naming)!!
                                            mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                                override fun onCancelled(p0: DatabaseError) {
                                                    print("Some Error happened!!")
                                                }

                                                override fun onDataChange(p0: DataSnapshot) {
                                                    for (ds in p0.children) {
                                                        ds.ref.removeValue()
                                                    }
                                                    var Ref = FirebaseDatabase.getInstance().getReference("CurrentOder/$custno")
                                                    var query: Query = Ref?.orderByChild("dishname")?.equalTo(naming)!!
                                                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                                                        override fun onCancelled(p0: DatabaseError) {

                                                        }

                                                        override fun onDataChange(p0: DataSnapshot) {
                                                            for (ds in p0.children) {
                                                                ds.ref.removeValue()
                                                            }
                                                        }
                                                    })

                                                    //

                                                    val ref = FirebaseDatabase.getInstance().getReference("OderHistory/$custno")
                                                    val user = history_model(mDate, mDishcost, naming, mrestname, "Failed","null")
                                                    var o = ref.push().key
                                                    ref.child(o!!).setValue(user)
                                                    Toast.makeText(this@Restro_side_dishes, "Item Removed", Toast.LENGTH_SHORT).show()
                                                    val smsManager = SmsManager.getDefault()
                                                    smsManager.sendTextMessage(custno, null, "THe specific order hsa been canceled from restaurant : Restaurant name "+ mrestname
                                                            + "\nDishname-"+naming+"\nDishcost:"+mDishcost+"\n For more detail Open App", null, null)
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

            button_confirm.setOnClickListener({
                var que: Query = FirebaseDatabase.getInstance().getReference("9853732536/Waffle House")
                que.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (ds in p0.children) {
                            val ref = FirebaseDatabase.getInstance().getReference("RestroOder/Waffle House")
                            var obj = RestroUserListModel(ds.child("address").value.toString(), ds.child("custno").value.toString(), ds.child("username").value.toString()
                                    , ds.child("restname").value.toString(), ds.child("odate").value.toString(), ds.child("dcost").value.toString(), ds.child("dname").value.toString())
                            var oderid = ref.push().key
                            ref.child(oderid!!).setValue(obj)
                        }
                    }

                })
                var delref = FirebaseDatabase.getInstance().getReference("9853732536/Waffle House")
                delref.setValue(null)

            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.restro_side, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id = item!!.itemId
        when (id) {
            R.id.rescuroder -> {
                var start = Intent(this@Restro_side_dishes, RestroCurrentOder::class.java)
                start.putExtra("pos", pos)
                startActivity(start)
            }
            R.id.restoderhist -> {
                var start = Intent(this@Restro_side_dishes,RestroSideOderHistory::class.java)
                start.putExtra("pos", pos)
                startActivity(start)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
