package com.org.asp.food_hut

class Model
{
    var Url: String?= null
    var Restro_name: String?= null

    constructor()
    {}

    fun GetUrl():String? {
        return this.Url
    }
    fun GetRestro_name(): String?
    {
        return this.Restro_name
    }
    fun SetUrl(Url: String)
    {
        this.Url=Url
    }
    fun SetRestro_name(Restro_name: String)
    {
        this.Restro_name=Restro_name
    }


}