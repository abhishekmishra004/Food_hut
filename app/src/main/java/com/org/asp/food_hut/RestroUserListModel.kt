package com.org.asp.food_hut

class RestroUserListModel
{
    var address = String()
    var custno = String()
    var username = String()
    var restname = String()
    var odate = String()
    var dcost = String()
    var dname = String()

    constructor()
    {

    }
    constructor(add:String,no: String,user:String, name:String, odate : String,dcost: String,dname: String)
    {
        this.address=add
        this.custno =no
        this.username=user
        this.restname =name
        this.odate=odate
        this.dcost=dcost
        this.dname=dname
    }
}