package com.org.asp.food_hut

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_viewspecial_dish.*

class view_dish : AppCompatActivity() {

    var dishimage: ImageView?=null
    var dishtext: TextView?=null
    var dishname : TextView?=null
    var dishcost: TextView?=null
    var bundleDishname: String?=null
    var bundleDishcost: String?=null
    var bundleDishdetail: String?=null
    var bundleDishurl: String?=null
    var button: ImageButton?=null
    var phone: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_dish)
        var actionbarr : ActionBar? = this!!.supportActionBar
        actionbarr?.title = "Dish"
        dishimage=findViewById(R.id.Dish_detail_image)
        dishtext = findViewById(R.id.Dish_details)
        dishname=findViewById(R.id.Dish_detail_name)
        dishcost=findViewById(R.id.Dish_detail_Cost)
        button=findViewById(R.id.ButtonAddCart)
        var inte = this.getIntent()
        var bun =inte.getBundleExtra("dish")
        bundleDishname= bun.getString("name","")
        bundleDishcost= bun.getString("cost","")
        bundleDishdetail= bun.getString("details","")
        var bundleRestname:String = bun.getString("restname")
        Resto_name.text =bundleRestname
        phone = bun.getString("val")
        //Toast.makeText(this@view_dish, phone, Toast.LENGTH_LONG).show()
        bundleDishurl=bun.getString("url","")
        Picasso.get().load(bundleDishurl).into(dishimage)
        dishname?.text=bundleDishname
        dishtext?.text=bundleDishdetail
        dishcost?.text= bundleDishcost


        button?.setOnClickListener({
            //Toast.makeText(this@view_dish, phone, Toast.LENGTH_LONG).show()
            val ref = FirebaseDatabase.getInstance().getReference("UsersCart/$phone")
            var dishid = ref.push().key
            var carte = viewSpecialDIshModel(bundleDishcost!!, bundleDishname!!,phone!!,bundleRestname )
            ref.child(dishid!!).setValue(carte).addOnCompleteListener {
                Toast.makeText(this@view_dish, "Item added to cart goto->home->Mycart", Toast.LENGTH_LONG).show()
                val startAct = Intent(this@view_dish, Home::class.java)
                startAct.putExtra("val", phone)
                startActivity(startAct)
                this@view_dish.finish()
            }
        })

    }
}
