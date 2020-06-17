package com.org.asp.food_hut

class CurrentUser
{
    private var uniqueId: String?= null
    private var phoneNo: String?= null
    private var type = String()

    constructor(uniqueId: String, phoneNo: String,Type:String) {
        this.uniqueId = uniqueId
        this.phoneNo = phoneNo
        this.type =Type
    }

    constructor()
    {
        this.uniqueId = null
        this.phoneNo = null
    }
    fun getuniqueId(): String?
    {
        return this.uniqueId
    }
    fun getPhoneNo(): String?
    {
        return this.phoneNo
    }
    fun getType():String
    {
        return  this.type
    }
}