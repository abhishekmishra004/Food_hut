package com.org.asp.food_hut

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_oderlist.*

class Oderlist : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oderlist)

        restlogout.setOnClickListener({
            val build = AlertDialog.Builder(this@Oderlist)
            build.setMessage("Are you sure want to Logout??")
                    .setCancelable(false)
                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            var unique = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                            var mfirebasedatabase = FirebaseDatabase.getInstance().getReference("CurrentUser").child(unique)
                            mfirebasedatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError) {

                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                    p0.ref.removeValue()
                                }

                            })
                            var start = Intent(this@Oderlist, Main_page::class.java)
                            startActivity(start)
                            this@Oderlist.finish()
                        }
                    })
                    .setNegativeButton("No", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, id: Int) {
                            dialog.cancel()
                        }
                    })
            var alert = build.create()
            alert.show()
        })

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
