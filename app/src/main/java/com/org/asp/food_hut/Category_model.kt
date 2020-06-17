package com.org.asp.food_hut

class Category_model {
    var Dishname: String?=null
    var Dishdetails: String?=null
    var Dishcost: String?=null
    var Dishurl: String?=null
    var RestroName: String?=null

    constructor()
    {}

    constructor(cost: String,details: String,dish: String,url:String,name:String)
    {
        Dishcost=cost
        Dishdetails=details
        Dishname=dish
        Dishurl=url
        RestroName=name

    }
}