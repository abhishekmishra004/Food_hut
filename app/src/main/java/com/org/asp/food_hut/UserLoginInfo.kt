package com.org.asp.food_hut

class UserLoginInfo
{
    private var username: String?= null
    private var phoneNo: String?= null
    private var password: String?=null
    private var type: String?=null

    constructor(username: String, phoneNo: String, password: String,ty:String) {
        this.username = username
        this.phoneNo = phoneNo
        this.password = password
        this.type =ty
    }

    constructor()
    {
        this.username = null
        this.phoneNo = null
        this.password = null
    }
    fun getpassword(): String?
    {
        return this.password
    }
    fun getUsername(): String
    {
        return this.username!!
    }
    fun getPhoneNO(): String?
    {
        return this.phoneNo
    }
    fun getType():String
    {
        return  this.type!!
    }

}