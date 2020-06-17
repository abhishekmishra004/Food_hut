package com.org.asp.food_hut

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.text.Layout
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_profile.*

class my_profile : AppCompatActivity() {

    var utexting: TextView? = null
    var photextting: TextView? = null
    var lay: RelativeLayout? = null
    var usertexts: String ?=null
    var phonetexts:String?=null
    var pro:ProgressBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        var actionbarr : ActionBar? = this!!.supportActionBar
        actionbarr?.title = "My profile"
        lay = findViewById(R.id.ChnagepassLayout)
        utexting = findViewById(R.id.Usertext)
        photextting = findViewById(R.id.phonetext)
        pro = findViewById(R.id.progress)
        pro?.visibility = View.INVISIBLE
        lay?.visibility = View.INVISIBLE
        val mIntent = intent
        val phoneNO: String = mIntent.getStringExtra("phoneNO")
        phonetexts=phoneNO
        val rootRef = FirebaseDatabase.getInstance().reference
        val checkUser = rootRef.child("UserLoginInfo/$phoneNO").ref
        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@my_profile, "Error Occured try again", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(UserLoginInfo::class.java)
                if (post != null) {
                    usertexts=post.getUsername()
                    utexting?.text = usertexts
                    photextting?.text = phoneNO
                } else {
                    Toast.makeText(this@my_profile, "Null pointing", Toast.LENGTH_LONG).show()
                }
            }

        }
        checkUser.addListenerForSingleValueEvent(postListener)
        Toast.makeText(this@my_profile, "profile", Toast.LENGTH_LONG).show()
        clickhandler()
    }



    fun clickhandler() {

        buttontempchange.setOnClickListener({
            lay?.visibility = View.VISIBLE
        })

        buttonchange.setOnClickListener( {
            pro?.visibility = View.VISIBLE
            var newpass:String = EditNewPassword.text.toString()
            var confnewpass = EditNewPasswordConfirm.text.toString()
            EditNewPassword?.isEnabled=false
            EditNewPasswordConfirm?.isEnabled= false
            Toast.makeText(this@my_profile,newpass,Toast.LENGTH_SHORT).show()
            Toast.makeText(this@my_profile,confnewpass,Toast.LENGTH_SHORT).show()
            if(newpass == confnewpass)
            {
                Toast.makeText(this@my_profile,"Password matches to confirm password",Toast.LENGTH_LONG).show()
                val ref = FirebaseDatabase.getInstance().getReference("UserLoginInfo")
                val user = UserLoginInfo(usertexts!!, phonetexts!!, newpass!!,"user")
                ref.child(phonetexts!!).setValue(user).addOnCompleteListener {
                    Toast.makeText(this@my_profile, "Password Chnages Successfully :)", Toast.LENGTH_LONG).show()

                    val startAct = Intent(this@my_profile, Main_page::class.java)
                    startActivity(startAct)
                    this@my_profile.finish()
                }
                lay?.visibility = View.INVISIBLE

            }
            else
            {
                Toast.makeText(this@my_profile,"Password doesnot matches to confirm password",Toast.LENGTH_LONG).show()
            }
        })
    }
}


