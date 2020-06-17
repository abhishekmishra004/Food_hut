package com.org.asp.food_hut

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class CurrentViewholder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var mView: View?=null
    var mDishname : TextView?=null
    var mDishcost: TextView?= null
    var mDate: TextView?=null
    var mrestname: TextView?=null
    var Status:TextView?=null
    var mClicklistener: CurrentViewholder.Clicklistener?=null

    init {
        mView = itemView!!
        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                mClicklistener?.onItemclick(view, adapterPosition)
            }
        })
    }

    fun setdetails(date: String, dishcost: String,dishname: String,restname:String,status: String)
    {
        mDishname= mView?.findViewById(R.id.currdishname)
        mDishcost = mView?.findViewById(R.id.currdishcost)
        mDate = mView?.findViewById(R.id.currdate)
        Status = mView?.findViewById(R.id.currstatus)
        mrestname =mView?.findViewById(R.id.currRestname)
        mDishname?.text = dishname
        mDishcost?.text = "Rs "+dishcost
        mDate?.text = date
        mrestname?.text =restname
        Status?.text="Status:"+status
    }
    interface Clicklistener{
        fun onItemclick(view: View,pos :Int)
    }
    fun setOnClickListner(clicklistener: Clicklistener)
    {
        mClicklistener=clicklistener
    }
}