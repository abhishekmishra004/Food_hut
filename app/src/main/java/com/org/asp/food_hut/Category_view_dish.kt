package com.org.asp.food_hut

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_category_view_dish.*

class Category_view_dish : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_view_dish)
        var pic :ImageView =findViewById(R.id.cat_Dish_detail_image)
        var inte = this.getIntent()
        var bun =inte.getBundleExtra("item")
        var restname =bun.getString("rname")
        var dishname = bun.getString("dname")
        var dcost = bun.getString("cost")
        var details =bun.getString("detail")
        var url =bun.getString("url")
        var phone =bun.getString("val")
        //Toast.makeText(this@Category_view_dish,"cost"+dcost+ "\ndname"+dishname
            //    +"\ndetail"+details+"\nurl"+url+"\nrname"+restname, Toast.LENGTH_LONG).show()
        cat_Resto_name.text =restname
        cat_Dish_detail_Cost.text = "Rs "+dcost
        cat_Dish_detail_name.text =dishname
        cat_Dish_details.text =details
        Picasso.get().load(url).into(pic)


        cat_ButtonAddCart.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("UsersCart/$phone")
            var dishid = ref.push().key

            var carte = viewSpecialDIshModel(dcost, dishname,phone,restname)
            ref.child(dishid!!).setValue(carte).addOnCompleteListener {
                Toast.makeText(this@Category_view_dish, "Item added to cart goto->home->Mycart", Toast.LENGTH_LONG).show()
                val startAct = Intent(this@Category_view_dish, Home::class.java)
                startAct.putExtra("val", phone)
                startActivity(startAct)
                this@Category_view_dish.finish()
            }

        }
    }
}
