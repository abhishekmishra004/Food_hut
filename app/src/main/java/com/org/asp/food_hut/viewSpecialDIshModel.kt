package com.org.asp.food_hut

class viewSpecialDIshModel
{
    var phoneno: String?=null
    var dishname:String?=null
    var dishcost:String?=null
    var Restroname:String?=null

    constructor()
    {}

    constructor(dishCost:String,dishName:String,phoneNo:String,naming:String)

    {
        this.phoneno=phoneNo
        this.dishname=dishName
        this.dishcost=dishCost
        this.Restroname=naming
    }

}