package com.org.asp.food_hut

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class Catergory_Viewholder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var mView: View?=null
    var mdishnametext : TextView?=null
    var mdishcosttext : TextView?=null
    var mrestronametext : TextView?=null
    var mClicklistener: Catergory_Viewholder.Clicklistener?=null


    init {
        mView = itemView!!
        itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View) {
                mClicklistener?.onItemclick(v,adapterPosition)
            }

        })
    }
    fun setdetails(mcontext: Context, dishcost: String, dishname: String, details:String, url: String,name:String)
    {
        mdishnametext =mView?.findViewById(R.id.dishnamecat)
        mdishcosttext =mView?.findViewById(R.id.dishcostcat)
        mrestronametext =mView?.findViewById(R.id.restnamecat)
        mdishcosttext?.text = "Rs "+dishcost
        mdishnametext?.text =dishname
        mrestronametext?.text =name

    }
    interface Clicklistener{
        fun onItemclick(view: View,pos :Int)
    }

    fun setOnClickListener(clicklistener: Clicklistener)
    {
        mClicklistener=clicklistener
    }
}