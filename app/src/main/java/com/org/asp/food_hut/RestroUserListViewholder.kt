package com.org.asp.food_hut

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class RestroUserListViewholder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var mView: View?=null
    var maddress : TextView?=null
    var mcustno : TextView?=null
    var musername : TextView?=null
    var mrestname : TextView?=null
    var mOdate : TextView?=null
    var mdcost : TextView?=null
    var mdname : TextView?=null
    var mClicklistener: RestroUserListViewholder.Clicklistener?=null

    init {
        mView = itemView!!

        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                mClicklistener?.onItemclick(view, adapterPosition)
            }
        })
    }

    fun setdetails(add: String,custno:String,username: String,restname:String,odate:String,dcost:String,dname:String)
    {
        maddress=mView?.findViewById(R.id.oaddress)
        mcustno =mView?.findViewById(R.id.ocustphoneno)
        musername =mView?.findViewById(R.id.ocustname)
        mrestname = mView?.findViewById(R.id.oRestname)
        mOdate =mView?.findViewById(R.id.ooderdate)
        mdcost = mView?.findViewById(R.id.odishcost)
        mdname = mView?.findViewById(R.id.odishname)
        maddress?.text ="Address: "+add
        mcustno?.text="Phone no: "+custno
        musername?.text=username
        mrestname?.text=restname
        mOdate?.text=odate
        mdcost?.text="Rs "+dcost
        mdname?.text =dname

    }

    interface Clicklistener{
        fun onItemclick(view: View,pos :Int)
    }
    fun setOnClickListner(clicklistener: Clicklistener)
    {
        mClicklistener=clicklistener
    }
}