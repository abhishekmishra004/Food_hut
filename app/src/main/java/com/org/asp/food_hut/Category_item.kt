package com.org.asp.food_hut

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Category_item : AppCompatActivity() {

    val rootRef = FirebaseDatabase.getInstance().reference
    var mRecyclerView: RecyclerView? = null
    var mfirebasedatabase: FirebaseDatabase? = null
    var mRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_item)
        mRecyclerView = findViewById(R.id.category_recy)
        val mIntent = intent
        val bundle:Bundle = mIntent.getBundleExtra("buni")
        var pos:String = bundle.getString("posi")
        var phone:String =bundle.getString("val")


        mRecyclerView?.layoutManager = LinearLayoutManager(this)

        Toast.makeText(this,pos, Toast.LENGTH_SHORT).show()
        if(pos=="1")
        {
            Toast.makeText(this,pos, Toast.LENGTH_SHORT).show()
            mfirebasedatabase = FirebaseDatabase.getInstance()
            mRef = mfirebasedatabase!!.getReference("NonVegDish")
            var mAdapter = object: FirebaseRecyclerAdapter<Category_model,Catergory_Viewholder>
            (Category_model::class.java,R.layout.category_image,Catergory_Viewholder::class.java,mRef)
            {
                override fun populateViewHolder(viewHolder: Catergory_Viewholder?, model: Category_model?, position: Int) {
                    viewHolder!!.setdetails(this@Category_item,model?.Dishcost!!,model?.Dishname!!,model?.Dishdetails!!,model?.Dishurl!!,model?.RestroName!!)

                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Catergory_Viewholder {

                    var holder: Catergory_Viewholder =super.onCreateViewHolder(parent, viewType)
                     holder.setOnClickListener(object : Catergory_Viewholder.Clicklistener{
                         override fun onItemclick(view: View, pos: Int) {
                             var bunde = Bundle()
                             bunde.putString("cost",getItem(pos).Dishcost)
                             bunde.putString("dname",getItem(pos).Dishname)
                             bunde.putString("detail",getItem(pos).Dishdetails)
                             bunde.putString("url",getItem(pos).Dishurl)
                             bunde.putString("rname",getItem(pos).RestroName)
                             Toast.makeText(this@Category_item,phone,Toast.LENGTH_LONG).show()
                             var int = Intent(this@Category_item,Category_view_dish::class.java)
                             bunde.putString("val",phone)
                             int.putExtra("item",bunde)
                             startActivity(int)
                         }

                     })
                    return holder
                }

            }
            mRecyclerView?.adapter =mAdapter
        }
        else if (pos=="2")
        {
            Toast.makeText(this,pos, Toast.LENGTH_SHORT).show()
            mfirebasedatabase = FirebaseDatabase.getInstance()
            mRef = mfirebasedatabase!!.getReference("VegDish")
            var mAdapter = object: FirebaseRecyclerAdapter<Category_model,Catergory_Viewholder>
            (Category_model::class.java,R.layout.category_image,Catergory_Viewholder::class.java,mRef)
            {
                override fun populateViewHolder(viewHolder: Catergory_Viewholder?, model: Category_model?, position: Int) {
                    viewHolder!!.setdetails(this@Category_item,model?.Dishcost!!,model?.Dishname!!,model?.Dishdetails!!,model?.Dishurl!!,model?.RestroName!!)

                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Catergory_Viewholder {

                    var holder: Catergory_Viewholder =super.onCreateViewHolder(parent, viewType)
                    holder.setOnClickListener(object : Catergory_Viewholder.Clicklistener{
                        override fun onItemclick(view: View, pos: Int) {
                            var bunde = Bundle()
                            bunde.putString("cost",getItem(pos).Dishcost)
                            bunde.putString("dname",getItem(pos).Dishname)
                            bunde.putString("detail",getItem(pos).Dishdetails)
                            bunde.putString("url",getItem(pos).Dishurl)
                            bunde.putString("rname",getItem(pos).RestroName)
                            Toast.makeText(this@Category_item,phone,Toast.LENGTH_LONG).show()
                            var int = Intent(this@Category_item,Category_view_dish::class.java)
                            bunde.putString("val",phone)
                            int.putExtra("item",bunde)
                            startActivity(int)
                        }

                    })
                    return holder
                }

            }
            mRecyclerView?.adapter =mAdapter

        }
        else if(pos=="3")
        {
            Toast.makeText(this,pos, Toast.LENGTH_SHORT).show()
            mfirebasedatabase = FirebaseDatabase.getInstance()
            mRef = mfirebasedatabase!!.getReference("Drinks")
            var mAdapter = object: FirebaseRecyclerAdapter<Category_model,Catergory_Viewholder>
            (Category_model::class.java,R.layout.category_image,Catergory_Viewholder::class.java,mRef)
            {
                override fun populateViewHolder(viewHolder: Catergory_Viewholder?, model: Category_model?, position: Int) {
                    viewHolder!!.setdetails(this@Category_item,model?.Dishcost!!,model?.Dishname!!,model?.Dishdetails!!,model?.Dishurl!!,model?.RestroName!!)

                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Catergory_Viewholder {

                    var holder: Catergory_Viewholder =super.onCreateViewHolder(parent, viewType)
                    holder.setOnClickListener(object : Catergory_Viewholder.Clicklistener{
                        override fun onItemclick(view: View, pos: Int) {
                            var bunde = Bundle()
                            bunde.putString("cost",getItem(pos).Dishcost)
                            bunde.putString("dname",getItem(pos).Dishname)
                            bunde.putString("detail",getItem(pos).Dishdetails)
                            bunde.putString("url",getItem(pos).Dishurl)
                            bunde.putString("rname",getItem(pos).RestroName)
                            Toast.makeText(this@Category_item,phone,Toast.LENGTH_LONG).show()
                            var int = Intent(this@Category_item,Category_view_dish::class.java)
                            bunde.putString("val",phone)
                            int.putExtra("item",bunde)
                            startActivity(int)
                        }

                    })
                    return holder
                }

            }
            mRecyclerView?.adapter =mAdapter
        }
        else if(pos=="4")
        {
            Toast.makeText(this,pos, Toast.LENGTH_SHORT).show()
            mfirebasedatabase = FirebaseDatabase.getInstance()
            mRef = mfirebasedatabase!!.getReference("Sweets")
            var mAdapter = object: FirebaseRecyclerAdapter<Category_model,Catergory_Viewholder>
            (Category_model::class.java,R.layout.category_image,Catergory_Viewholder::class.java,mRef)
            {
                override fun populateViewHolder(viewHolder: Catergory_Viewholder?, model: Category_model?, position: Int) {
                    viewHolder!!.setdetails(this@Category_item,model?.Dishcost!!,model?.Dishname!!,model?.Dishdetails!!,model?.Dishurl!!,model?.RestroName!!)

                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Catergory_Viewholder {

                    var holder: Catergory_Viewholder =super.onCreateViewHolder(parent, viewType)
                    holder.setOnClickListener(object : Catergory_Viewholder.Clicklistener{
                        override fun onItemclick(view: View, pos: Int) {
                            var bunde = Bundle()
                            bunde.putString("cost",getItem(pos).Dishcost)
                            bunde.putString("dname",getItem(pos).Dishname)
                            bunde.putString("detail",getItem(pos).Dishdetails)
                            bunde.putString("url",getItem(pos).Dishurl)
                            bunde.putString("rname",getItem(pos).RestroName)
                            Toast.makeText(this@Category_item,phone,Toast.LENGTH_LONG).show()
                            var int = Intent(this@Category_item,Category_view_dish::class.java)
                            bunde.putString("val",phone)
                            int.putExtra("item",bunde)
                            startActivity(int)
                        }

                    })
                    return holder
                }

            }
            mRecyclerView?.adapter =mAdapter
        }
    }
}
