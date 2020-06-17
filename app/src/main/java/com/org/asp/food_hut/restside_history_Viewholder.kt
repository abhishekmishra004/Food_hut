package com.org.asp.food_hut

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class restside_history_Viewholder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var mView: View?=null
    var maddress : TextView?=null
    var mcustno : TextView?=null
    var musername : TextView?=null
    var mrestname : TextView?=null
    var mOdate : TextView?=null
    var mdcost : TextView?=null
    var mdname : TextView?=null
    var mtrans:TextView?=null


    init {
        mView = itemView!!}

    fun setdetails(addr:String,custno:String, user: String,rest:String,odate:String,dcost:String,dname:String,transId:String)
    {
        maddress =mView?.findViewById(R.id.Haddress)
        mcustno =mView?.findViewById(R.id.Hcustphoneno)
        musername =mView?.findViewById(R.id.Hcustname)
        mrestname =mView?.findViewById(R.id.HRestname)
        mOdate= mView?.findViewById(R.id.Hoderdate)
        mdcost = mView?.findViewById(R.id.Hdishcost)
        mdname = mView?.findViewById(R.id.Hdishname)
        mtrans =mView?.findViewById(R.id.Htransid)

        maddress?.text =addr
        mcustno?.text = custno
        musername?.text =user
        mrestname?.text =rest
        mOdate?.text =odate
        mdcost?.text = "Rs "+ dcost
        mdname?.text =dname
        mtrans?.text =transId
    }
}