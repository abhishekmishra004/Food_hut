package com.org.asp.food_hut

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class  Viewholder2: RecyclerView.ViewHolder
{
    var mView: View ?=null
    var mDishCost: TextView?=null
    var mDishname: TextView?=null
    var mDishimage: ImageView?=null
    var mDishdetails:String?=null
    var mClicklistener: Viewholder2.Clicklistener?=null


    constructor(itemView: View?) : super(itemView) {
        mView = itemView!!

        itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View) {
                mClicklistener?.onItemclick(v,adapterPosition)
            }

        })
    }

    interface Clicklistener{
        fun onItemclick(view: View,pos :Int)
    }

    fun setOnClickListner(clicklistener: Clicklistener)
    {
        mClicklistener=clicklistener
    }


    fun setdetails(context: Context,dishcost: String,dishdetails:String, dishname: String, url : String)
    {
        mDishdetails=dishdetails
        mDishCost=mView?.findViewById(R.id.DishCost)
        mDishname = mView?.findViewById(R.id.DishName)
        mDishimage = mView?.findViewById(R.id.DishImage)
        mDishCost?.text = dishcost
        mDishname?.text = dishname
        Picasso.get().load(url).into(mDishimage)
    }

    fun getDetails():Bundle?
    {
        val bundle = Bundle()
        var test =mDishname?.text.toString()
        var test2=mDishCost?.text.toString()
        if(test==null)
            test=""
        if(test2==null)
            test2=""
        bundle.putString("name",test)
        bundle.putString("cost",test2)
        bundle.putString("details",mDishdetails)
        return bundle
    }
}