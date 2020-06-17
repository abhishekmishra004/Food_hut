package com.org.asp.food_hut

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SplashScreen : AppCompatActivity() {


    private var permissionString = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.PROCESS_OUTGOING_CALLS,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.READ_SMS)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (!haspermission(this, *permissionString)) {
            ActivityCompat.requestPermissions(this@SplashScreen, permissionString, 131)
        } else {
            delay()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            131 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED
                        && grantResults[5] == PackageManager.PERMISSION_GRANTED) {
                    delay()
                } else {
                    Toast.makeText(this@SplashScreen, "please grant all permission", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
                return
            }
            else -> {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                this.finish()
                return
            }
        }
    }


    fun haspermission(context: Context, vararg permissions: String): Boolean {
        var hasallpermission = true
        for (permission in permissions) {
            val res = context.checkCallingOrSelfPermission(permission)
            if (res != PackageManager.PERMISSION_GRANTED) {
                hasallpermission = false
            }
        }
        return hasallpermission
    }


    fun delay() {
        Handler().postDelayed({
            var unique = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            var mfirebasedatabase = FirebaseDatabase.getInstance().getReference("CurrentUser").child(unique)
//            var mquery: Query = mfirebasedatabase.orderByChild("uniqueId").equalTo(unique)
            mfirebasedatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    if (p0 != null) {
                        val post = p0.getValue(CurrentUser::class.java)
                        var st = post?.getuniqueId()
                        var type = post?.getType()
                        if (st.equals(unique)) {
                            var type = post?.getType()
                            if (type == "user") {
                                var phone = post?.getPhoneNo()
                                //Toast.makeText(this@SplashScreen, "Found" + post?.getuniqueId() + phone, Toast.LENGTH_SHORT).show()
                                val startAct = Intent(this@SplashScreen, Home::class.java)
                                startAct.putExtra("val", phone)
                                startActivity(startAct)
                                this@SplashScreen.finish()
                            }
                            else{
                                val startAct = Intent(this@SplashScreen, Oderlist::class.java)
                                startActivity(startAct)
                                this@SplashScreen.finish()
                            }
                        } else {
                            Toast.makeText(this@SplashScreen, "not found" + st, Toast.LENGTH_SHORT).show()
                            val startAct = Intent(this@SplashScreen, Main_page::class.java)
                            startActivity(startAct)
                            this@SplashScreen.finish()
                        }

                    } else {
                        Toast.makeText(this@SplashScreen, "not found", Toast.LENGTH_SHORT).show()
                        val startAct = Intent(this@SplashScreen, Main_page::class.java)
                        startActivity(startAct)
                        this@SplashScreen.finish()
                    }
                }

            })
            //if(current==true)
            //Toast.makeText(this@SplashScreen,"cur "+current,Toast.LENGTH_SHORT).show()

        }, 1000)
    }
}
