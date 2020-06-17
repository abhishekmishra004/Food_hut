package com.org.asp.food_hut

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

class Viewholder : RecyclerView.ViewHolder
{

    var mView: View ?=null
    var restro_name: String?=null
    var mImageRestro : ImageView ?=null
    var mClicklistener: Viewholder.Clicklistener?=null


    constructor(itemView: View?) : super(itemView) {
        mView = itemView!!

        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                mClicklistener?.onItemclick(view, adapterPosition)
            }
        })

    }

    fun setDetails(mcontext: Context,Restro_name: String, Url: String)
    {
        restro_name=Restro_name
        mImageRestro= mView?.findViewById(R.id.RestroImage)!!
        Picasso.get().load(Url).into(mImageRestro)
    }

    interface Clicklistener{
        fun onItemclick(view: View,pos :Int)
    }

     fun setOnClickListner(clicklistener: Clicklistener)
    {
        mClicklistener=clicklistener
    }
}