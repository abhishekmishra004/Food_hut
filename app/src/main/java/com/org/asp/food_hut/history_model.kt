package com.org.asp.food_hut

class history_model
{
    var date:String?=null
    var dishcost:String?= null
    var dishname:String?=null
    var restroname:String?=null
    var deliveryStatus:String?=null
    var transid: String?=null


    constructor()
    {

    }

    constructor(Date: String?, Dishcost: String?, Dishname: String?,name: String,status:String,transid:String) {
        this.date = Date
        this.dishname = Dishname
        this.dishcost = Dishcost
        this.restroname=name
        this.deliveryStatus = status
        this.transid = transid
    }

}