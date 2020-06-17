package com.org.asp.food_hut

class special
{
    var Dishcost: String?=null
    var Dishdetails:String?=null
    var Dishname:String?=null
    var Dishurl:String?=null
    var RestroName: String?=null

    constructor()
    {

    }

    constructor(cost:String,details:String,name:String,url:String,restname:String)
    {
        this.Dishcost=cost
        this.Dishdetails=details
        this.Dishname=name
        this.Dishurl=url
        this.RestroName=restname
    }

    fun getcost():String?
    {
        return this.Dishcost
    }
    fun getdetails():String?
    {
        return this.Dishdetails
    }
    fun getname():String?
    {
        return this.Dishname
    }
    fun geturl():String?
    {
        return this.Dishurl
    }
}