package com.org.asp.food_hut

class RestroSideDeliverdModel {
    var address = String()
    var custno = String()
    var username = String()
    var restname = String()
    var odate = String()
    var dcost = String()
    var dname = String()
    var transid =String()

    constructor()
    {

    }
    constructor(add:String,no: String,user:String, name:String, odate : String,dcost: String,dname: String,trans:String)
    {
        this.address=add
        this.custno =no
        this.username=user
        this.restname =name
        this.odate=odate
        this.dcost=dcost
        this.dname=dname
        this.transid =trans
    }
}