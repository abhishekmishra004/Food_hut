package com.org.asp.food_hut

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class Viewholder3(itemView: View?) : RecyclerView.ViewHolder(itemView)
{
    var mView: View?=null
    var mDishname :TextView?=null
    var mDishcost: TextView?= null
    var mphone : TextView?= null
    var mClicklistener: Viewholder3.Clicklistener?=null
    var mrestname: TextView?=null


    init {
        mView = itemView!!
        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                mClicklistener?.onItemclick(view, adapterPosition)
            }
        })
    }
    fun setdetails(mcontext: Context,dishcost: String,dishname: String,phoneno: String,name:String)
    {
        mDishcost = mView?.findViewById(R.id.dishcost)
        mDishname = mView?.findViewById(R.id.dishname)
        mphone = mView?.findViewById(R.id.phoneno)
        mrestname = mView?.findViewById(R.id.Restname)
        mDishcost?.text = dishcost
        mDishname?.text = dishname
        mphone?.text = phoneno
        mrestname?.text =name

    }
    interface Clicklistener{
        fun onItemclick(view: View,pos :Int)
    }
    fun setOnClickListner(clicklistener: Clicklistener)
    {
        mClicklistener=clicklistener
    }
}