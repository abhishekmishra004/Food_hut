package com.org.asp.food_hut

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telephony.SmsManager
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*

class RestroCurrentOder : AppCompatActivity() {
    var mRecyclerView: RecyclerView? = null
    var pos = String()
    var mAdapter: FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restro_current_oder)
        val mIntent = intent
        mRecyclerView = findViewById(R.id.restCurrSideRecy)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        pos = mIntent.getStringExtra("pos")
        if (pos == "1") {
            var mRef = FirebaseDatabase.getInstance().getReference("RestroOder/Adda Unpluged")
            save(mRef)

        } else if (pos == "2") {
            var mRef = FirebaseDatabase.getInstance().getReference("RestroOder/Hideaway Restro & Cafe")
            save(mRef)

        } else if (pos == "3") {
            var mRef = FirebaseDatabase.getInstance().getReference("RestroOder/Michael's Kitchen")
            save(mRef)

        } else if (pos == "4") {
            var mRef = FirebaseDatabase.getInstance().getReference("RestroOder/Paris Bakery")
            save(mRef)

        } else if (pos == "5") {
            var mRef = FirebaseDatabase.getInstance().getReference("RestroOder/The Dugout")
            save(mRef)

        } else if (pos == "6") {
            var mRef = FirebaseDatabase.getInstance().getReference("RestroOder/The Restaurant")
            save(mRef)

        } else if (pos == "7") {
            var mRef = FirebaseDatabase.getInstance().getReference("RestroOder/Waffle House")
            save(mRef)

        }

    }
     fun save(ds: DatabaseReference)
     {
         mAdapter = object : FirebaseRecyclerAdapter<RestroUserListModel, RestroUserListViewholder>(
                 RestroUserListModel::class.java, R.layout.oder_list_image, RestroUserListViewholder::class.java, ds) {
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
                         var add =getItem(pos).address
                         var username = getItem(pos).username
                         val build = AlertDialog.Builder(this@RestroCurrentOder)
                         build.setMessage("Is the food item is delivered??")
                                 .setCancelable(false)
                                 .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                                     override fun onClick(dialog: DialogInterface, id: Int) {
                                         var mquery: Query = ds?.orderByChild("dname")?.equalTo(naming)!!
                                         mquery.addListenerForSingleValueEvent(object : ValueEventListener {
                                             override fun onCancelled(p0: DatabaseError) {
                                                 print("Some Error happened!!")
                                             }

                                             override fun onDataChange(p0: DataSnapshot) {
                                                 for (ds in p0.children) {
                                                     ds.ref.removeValue()
                                                 }
                                             }
                                         })
                                         var ref = FirebaseDatabase.getInstance().getReference("RestroOderHistory/$mrestname")
                                         var oderid = ref.push().key
                                         val user = RestroSideDeliverdModel(add,custno,username,mrestname,mDate,mDishcost,naming,oderid!!)
                                         ref.child(oderid!!).setValue(user)

                                         val Uref = FirebaseDatabase.getInstance().getReference("OderHistory/$custno")
                                         val Uuser = history_model(mDate, mDishcost, naming, mrestname, "Delivered",oderid)
                                         var o = Uref.push().key
                                         Uref.child(o!!).setValue(Uuser)

                                         val smsManager = SmsManager.getDefault()
                                         smsManager.sendTextMessage( custno, null, "THe specific order hsa been DELIVERED : Restaurant name "+ mrestname
                                                 + "\nDishname-"+naming+"\nDishcost:"+ mDishcost+"\n For more detail Open App", null, null)

                                         var mref = FirebaseDatabase.getInstance().getReference("CurrentOder/$custno").orderByChild("dishname").equalTo(naming)
                                         mref.addListenerForSingleValueEvent(object : ValueEventListener{
                                             override fun onDataChange(p0: DataSnapshot) {
                                                 for (d in p0.children)
                                                 {
                                                     d.ref.removeValue()
                                                 }
                                             }

                                             override fun onCancelled(p0: DatabaseError) {

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
}
