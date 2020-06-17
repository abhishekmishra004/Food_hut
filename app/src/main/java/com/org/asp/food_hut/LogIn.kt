package com.org.asp.food_hut

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_log_in.*


class LogIn : AppCompatActivity() {

    var LOGIN: ImageButton? = null
    var userId: String? = null
    var pass: String? = null
    var userEdit: EditText? = null
    var passEdit: EditText? = null
    var passed: TextView? = null
    var progressbar: ProgressBar? = null
    var Text: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        Text =findViewById(R.id.forget)
        LOGIN = findViewById(R.id.loginButton)
        userEdit = findViewById(R.id.phone_edit_text)
        passEdit = findViewById(R.id.pass_edit_text)
        progressbar = findViewById(R.id.progressBar)
        //passed = findViewById(R.id.pass)
        clickHandler()
    }

    fun clickHandler() {
        LOGIN?.setOnClickListener {
            try {
                progressbar?.visibility = View.VISIBLE
                userId = userEdit!!.getText().toString()
                pass = passEdit!!.getText().toString()
                if (TextUtils.isEmpty(userId) or TextUtils.isEmpty(pass)) {
                    Toast.makeText(this@LogIn, "Please Enter Some Value", Toast.LENGTH_SHORT).show()
                    progressBar?.visibility = View.INVISIBLE
                } else if (!android.util.Patterns.PHONE.matcher(userId).matches()) {
                    userEdit!!.error = "PLEASE ENTER CORRECT VALUE"
                    userEdit!!.requestFocus()
                    progressBar?.visibility = View.INVISIBLE
                } else {
                    try {
                        val rootRef = FirebaseDatabase.getInstance().reference
                        val checkUser = rootRef.child("UserLoginInfo/$userId").ref
                        val postListener = object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                                Toast.makeText(this@LogIn, "User doesnot exists", Toast.LENGTH_LONG).show()
                            }

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val post = dataSnapshot.getValue(UserLoginInfo::class.java)
                                if (post != null) {
                                    if (post.getpassword() == pass) {
                                        val alertDialog = AlertDialog.Builder(this@LogIn)
                                                .setTitle("Success")
                                                .setMessage("Logged in successfully Welcome " + post.getUsername())
                                                .setPositiveButton("OK", null)
                                                .create()
                                        alertDialog.show()
                                        progressBar?.visibility = View.INVISIBLE
                                        //Write in Current user
                                        val curruser: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                                        val phnno:String? = userId
                                        val ref = FirebaseDatabase.getInstance().getReference("CurrentUser")
                                        val current = CurrentUser(curruser,phnno!!,post.getType())
                                        ref.child(curruser).setValue(current).addOnCompleteListener {  }
                                        Toast.makeText(this@LogIn, "test phone" + userId, Toast.LENGTH_SHORT).show()
                                        if(post.getType() == "user") {
                                            val startAct = Intent(this@LogIn, Home::class.java)
                                            startAct.putExtra("val", userId)
                                            startActivity(startAct)
                                            this@LogIn.finish()

                                        }
                                        else
                                        {
                                            val startAct = Intent(this@LogIn,Oderlist::class.java)
                                            startAct.putExtra("val", userId)
                                            startActivity(startAct)
                                            this@LogIn.finish()

                                            // MOVE TO RESTRO PAGE

                                        }
                                    } else if (post.getpassword() != pass) {
                                        Toast.makeText(this@LogIn, "Password doesnot match", Toast.LENGTH_LONG).show()
                                        progressBar?.visibility = View.INVISIBLE
                                    }
                                } else {
                                    Toast.makeText(this@LogIn, "User doesnot existsss", Toast.LENGTH_LONG).show()
                                    progressBar?.visibility = View.INVISIBLE
                                }
                            }
                        }
                        checkUser.addListenerForSingleValueEvent(postListener)
                    } catch (e: Exception) {
                        Toast.makeText(this@LogIn, "Some Error Happened", Toast.LENGTH_SHORT).show()
                        progressBar?.visibility = View.INVISIBLE
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@LogIn, "Some Error Happened", Toast.LENGTH_SHORT).show()
                progressBar?.visibility = View.INVISIBLE
            }
        }

        Text?.setOnClickListener(
                {
                    var start = Intent(this@LogIn,Signup::class.java)
                    start.putExtra("change",1)
                    startActivity(start)
                    this@LogIn.finish()
                }
        )

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val startAct = Intent(this@LogIn, Main_page::class.java)
        startActivity(startAct)
        this.finish()
    }
}
