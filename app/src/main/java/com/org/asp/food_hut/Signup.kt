package com.org.asp.food_hut

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.org.asp.food_hut.R.drawable.signupb1
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.concurrent.TimeUnit

class Signup : AppCompatActivity() {

    var userName: String? = null
    var passWord: String? = null
    var phoneNO: String? = null
    var confirmPass: String? = null
    var SIGNUP: ImageButton? = null
    var userNameEdit: EditText? = null
    var passWordEdit: EditText? = null
    var phoneNOEdit: EditText? = null
    var confirmPassEdit: EditText? = null
    var againLoginText: TextView? = null
    val contryCode = "+91"
    var progressBar: ProgressBar? = null
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth
    var verificationId = ""
    lateinit var testUser: Class<UserLoginInfo>
    var change:Int?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        SIGNUP = findViewById(R.id.sign_up_button_signup)
        progressBar = findViewById(R.id.progressBar)
        userNameEdit = findViewById(R.id.user_edit_text)
        passWordEdit = findViewById(R.id.pass_edit_text)
        phoneNOEdit = findViewById(R.id.phone_edit_text)
        confirmPassEdit = findViewById(R.id.confirm_pass_edit_text)
        againLoginText = findViewById(R.id.again_login)
        val mIntent = intent
         change = mIntent.getIntExtra("change",0)
        if(change==1)
        {
            sign_up_button_signup.setBackgroundResource(R.drawable.change_pass)
            againLoginText?.visibility =View.INVISIBLE
        }

        val alertDialog = AlertDialog.Builder(this)
                .setTitle("PLEASE READ IT ALERT!!!")
                .setMessage("Signing up will get an OTP on your phone and it might receive an SMS message for verification and standard rates may apply and the only phone number having in the phone can login!!")
                .setPositiveButton("OK", null)
                .create()

        alertDialog.show()
        mAuth = FirebaseAuth.getInstance()

        //if(mAuth.currentUser == null)
        // {
        //if user has not signed in
        // }

        clickHandler()
    }


    private fun verificationCallbacks() {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signIn(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException?) {
                alert("Firebase Exception")
                progressBar?.visibility = View.INVISIBLE
                userNameEdit!!.isEnabled = true
                phoneNOEdit!!.isEnabled = true
                passWordEdit!!.isEnabled = true
                confirmPassEdit!!.isEnabled = true
            }

            override fun onCodeSent(verfication: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(verfication, p1)
                verificationId = verfication.toString()

            }

        }
    }

    private fun signIn(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        try {
                            userName = userNameEdit!!.text.toString()
                            phoneNO = phoneNOEdit!!.text.toString()
                            passWord = passWordEdit!!.text.toString()
                            if (TextUtils.isEmpty(userName) or TextUtils.isEmpty(passWord) or TextUtils.isEmpty(phoneNO) or TextUtils.isEmpty(confirmPass)) {
                                Toast.makeText(this@Signup, "Please Enter Correct Value text", Toast.LENGTH_SHORT).show()
                            } else if (!android.util.Patterns.PHONE.matcher(phoneNO).matches()) {
                                phoneNOEdit!!.setError("Please enter correct Phone number")
                                phoneNOEdit!!.requestFocus()
                            } else if (passWord!!.length < 6) {
                                passWordEdit!!.setError("Minimum password size is 6")
                                passWordEdit!!.requestFocus()
                            }
                            else{

                            try {
                                val rootRef = FirebaseDatabase.getInstance().reference
                                val checkUser = rootRef.child("UserLoginInfo").ref
                                val postListener: ValueEventListener = object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {
                                        alert("Database error")
                                        progressBar?.visibility = View.INVISIBLE
                                    }

                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        if(change==1)
                                        {write()}
                                        else{
                                        if (dataSnapshot.hasChild(phoneNO!!)) {
                                            alert("Phone no exists")
                                            progressBar?.visibility = View.INVISIBLE
                                        } else {
                                            write()
                                        }}
                                    }

                                }
                                checkUser.addListenerForSingleValueEvent(postListener)
                            } catch (e: Exception) {
                                Toast.makeText(this@Signup, "User already exists Registration Failed!!", Toast.LENGTH_LONG).show()
                                progressBar?.visibility = View.INVISIBLE
                                userNameEdit!!.isEnabled = true
                                phoneNOEdit!!.isEnabled = true
                                passWordEdit!!.isEnabled = true
                                confirmPassEdit!!.isEnabled = true
                                val startAct = Intent(this@Signup, Main_page::class.java)
                                startActivity(startAct)
                                this@Signup.finish()
                            }}

                        } catch (e: Exception) {
                            Toast.makeText(this, "Some Error Happened", Toast.LENGTH_SHORT).show()
                            userNameEdit!!.isEnabled = true
                            phoneNOEdit!!.isEnabled = true
                            passWordEdit!!.isEnabled = true
                            confirmPassEdit!!.isEnabled = true
                        }

                    }
                }
    }

    private fun verify() {

        verificationCallbacks()

        val phnNo = phoneNOEdit!!.text.toString()
        val phoneNumberCode = "$contryCode$phnNo"
        //Toast.makeText(this, phoneNumberCode, Toast.LENGTH_SHORT).show()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumberCode,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        )
    }


    fun clickHandler() {

        SIGNUP?.setOnClickListener {
            passWord = passWordEdit!!.getText().toString()
            confirmPass = confirmPassEdit!!.getText().toString()
            // Toast.makeText(this@Signup, userName, Toast.LENGTH_SHORT).show()
            // Toast.makeText(this@Signup, phoneNO, Toast.LENGTH_SHORT).show()
            // Toast.makeText(this@Signup, passWord, Toast.LENGTH_SHORT).show()
            // Toast.makeText(this@Signup, confirmPass, Toast.LENGTH_SHORT).show()

            //try{

            if (passWord.equals(confirmPass)) {
                verify()
                progressBar?.visibility = View.VISIBLE
                userNameEdit!!.isEnabled = false
                phoneNOEdit!!.isEnabled = false
                passWordEdit!!.isEnabled = false
                confirmPassEdit!!.isEnabled = false

            } else {
                confirmPassEdit!!.setError("Password doesnot match")
                confirmPassEdit!!.requestFocus()

            }
            //}
            /** catch (e:Exception)
            {
            Toast.makeText(this@Signup,"Username Exists",Toast.LENGTH_SHORT).show()
            }*/
        }

        againLoginText?.setOnClickListener({
            val startAct = Intent(this@Signup, LogIn::class.java)
            startActivity(startAct)
            this.finish()
        })
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val startAct = Intent(this@Signup, Main_page::class.java)
        startActivity(startAct)
        this.finish()
    }

    fun write() {
        val ref = FirebaseDatabase.getInstance().getReference("UserLoginInfo")
        var type  ="user"
        val user = UserLoginInfo(userName!!, phoneNO!!, passWord!!,type)
        ref.child(phoneNO!!).setValue(user).addOnCompleteListener {
            Toast.makeText(this@Signup, "Successful :)", Toast.LENGTH_LONG).show()
            progressBar?.visibility = View.INVISIBLE
            val startAct = Intent(this@Signup, Main_page::class.java)
            startActivity(startAct)
            this@Signup.finish()
        }
    }

    fun alert(str: String) {
        val alertDialog = AlertDialog.Builder(this@Signup)
                .setTitle("Failed!!!")
                .setMessage(str)
                .setPositiveButton("OK", null)
                .create()

        alertDialog.show()
    }
}
