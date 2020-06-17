package com.org.asp.food_hut

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class History_Viewholder(itemView: View?) : RecyclerView.ViewHolder(itemView)
{
    var mView: View?=null
    var mDishname : TextView?=null
    var mDishcost: TextView?= null
    var mDate:TextView?=null
    var mrestname:TextView?=null
    var Status : TextView?=null
    var mTrans : TextView?=null


    init {
        mView = itemView!!
    }

    fun setdetails(date: String, dishcost: String,dishname: String,restname:String,status: String,trans:String)
    {
        mDishname= mView?.findViewById(R.id.HistDishnameText)
        mDishcost = mView?.findViewById(R.id.HistDishcostText)
        mDate = mView?.findViewById(R.id.HistDishdateText)
        Status = mView?.findViewById(R.id.histStatus)
        mrestname =mView?.findViewById(R.id.Histrestroname)
        mTrans = mView?.findViewById(R.id.transId)
        mDishname?.text = dishname
        mDishcost?.text = "Rs "+dishcost
        mDate?.text = date
        mrestname?.text =restname
        Status?.text="Status: "+status
        mTrans?.text ="Transaction id:" + trans
    }
}