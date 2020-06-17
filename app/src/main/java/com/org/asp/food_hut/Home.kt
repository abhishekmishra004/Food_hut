package com.org.asp.food_hut

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.provider.Settings
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*

class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    var progressbar: ProgressBar? = null
    var mRecyclerView: RecyclerView? = null
    var mfirebasedatabase: FirebaseDatabase? = null
    var mRef: DatabaseReference? = null
    var mAdapter: FirebaseRecyclerAdapter<Model, Viewholder>? = null
    var mdrawerlayout: DrawerLayout? = null
    var mtoogle: ActionBarDrawerToggle? = null
    var mnavigarionview: NavigationView? = null
    var phoneNO: String? = null
    var image1: ImageView? = null
    var image2: ImageView? = null
    var image3: ImageView? = null
    var image4: ImageView? = null
    var image5: ImageView? = null
    val dish = arrayOf<String>("Red Velvet Pancake", "Waffle cake","Chicken Sandwich","Butter Chicken","Grilled Chicken",
            "Butter Chicken twin Sandwich","Masala Dhosa","Mater Paneer","Veg Hakka Noodles","Margherita Pizza","Palak Paneer","Tandoori Chicken")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        var actionbarr: ActionBar? = this!!.supportActionBar
        actionbarr?.title = "Home"

        var edit : AutoCompleteTextView = findViewById(R.id.searchedittext)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dish)
        edit.setAdapter(adapter)

        mRecyclerView = findViewById(R.id.RecyclerRestro)
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mfirebasedatabase = FirebaseDatabase.getInstance()
        mRef = mfirebasedatabase!!.getReference("Restro_list")
        mdrawerlayout = findViewById(R.id.nav_home)
        mnavigarionview = findViewById(R.id.nav_view)
        image1 = findViewById(R.id.Baby_corn_image)
        image2 = findViewById(R.id.Hydrabadi_veg_image)
        image3 = findViewById(R.id.spaghetti)
        image4 = findViewById(R.id.matarpaneer_image)
        image5 = findViewById(R.id.tandoori_image)
        val mIntent = intent
        phoneNO = mIntent.getStringExtra("val")
        mnavigarionview?.setNavigationItemSelectedListener(this)
        mtoogle = ActionBarDrawerToggle(this, mdrawerlayout, R.string.Open, R.string.Close)
        mdrawerlayout?.addDrawerListener(mtoogle!!)
        mtoogle?.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mAdapter = object : FirebaseRecyclerAdapter<Model, Viewholder>(
                Model::class.java, R.layout.restro_image, Viewholder::class.java, mRef) {

            override fun populateViewHolder(viewHolder: Viewholder?, model: Model?, position: Int) {
                val mcontext: Context = this@Home
                viewHolder!!.setDetails(mcontext, model?.GetRestro_name()!!, model?.GetUrl()!!)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
                var viewholder: Viewholder = super.onCreateViewHolder(parent, viewType)
                viewholder.setOnClickListner(object : Viewholder.Clicklistener {
                    override fun onItemclick(view: View, pos: Int) {
                        var test = pos
                        test++
                        //Toast.makeText(context,"pos is " +test,Toast.LENGTH_LONG).show()
                        //var rest: Restro_dishes?
                        //rest!!.setpos(test)
                        val intent = Intent(this@Home, Restro_dishes::class.java)
                        val bundle = Bundle()
                        bundle.putString("val", phoneNO)
                        bundle.putString("position", test.toString())
                        intent.putExtra("pos", bundle)
                        startActivity(intent)


                    }

                })
                return viewholder
            }
        }
        mRecyclerView?.adapter = mAdapter

        clickhandler()

        button_search.setOnClickListener({
            var st = edit.text.toString()
            if(st == "Red Velvet Pancake")
            {
                var stri = "Dishes/7/2"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Waffle cake")
            {
                var stri = "Dishes/7/5"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Chicken Sandwich")
            {
                var stri = "Dishes/1/2"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Butter Chicken")
            {
                var stri = "Dishes/3/1"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Grilled Chicken")
            {
                var stri = "Dishes/3/3"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Butter Chicken twin Sandwich")
            {
                var stri = "Dishes/4/1"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Masala Dhosa")
            {
                var stri = "Dishes/4/2"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Mater Paneer")
            {
                var stri = "Dishes/4/3"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Veg Hakka Noodles")
            {
                var stri = "Dishes/4/4"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Margherita Pizza")
            {
                var stri = "Dishes/5/3"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Palak Paneer")
            {
                var stri = "Dishes/6/2"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else if (st=="Tandoori Chicken")
            {
                var stri = "Dishes/6/4"
                var start =Intent(this@Home,searched_dish::class.java)
                var bun = Bundle()
                bun.putString("link",stri)
                bun.putString("val",phoneNO)
                start.putExtra("bun",bun)
                startActivity(start)
            }
            else
            {
                Toast.makeText(this@Home,"No dish found",Toast.LENGTH_SHORT).show()
            }

        })

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        when (id) {
            R.id.mypro -> {

                var start = Intent(this@Home, my_profile::class.java)
                start.putExtra("phoneNO", phoneNO)
                startActivity(start)
            }
            R.id.mycart -> {
                var start1 = Intent(this@Home, Cart::class.java)
                start1.putExtra("phoneNO", phoneNO)
                startActivity(start1)
            }
            R.id.curOrder -> {
                var start1 = Intent(this@Home, CurrentOder::class.java)
                start1.putExtra("phoneNO", phoneNO)
                startActivity(start1)
            }
            R.id.myoder -> {
                var start2 = Intent(this@Home, oders::class.java)
                start2.putExtra("phoneNO", phoneNO)
                startActivity(start2)

            }
            R.id.logout -> {

                val build = AlertDialog.Builder(this@Home)
                build.setMessage("Are you sure want to Logout??")
                        .setCancelable(false)
                        .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                var unique = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                                var mfirebasedatabase = FirebaseDatabase.getInstance().getReference("CurrentUser").child(unique)
                                mfirebasedatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError) {

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        p0.ref.removeValue()
                                    }

                                })
                                var start = Intent(this@Home, Main_page::class.java)
                                startActivity(start)
                                this@Home.finish()
                            }
                        })
                        .setNegativeButton("No", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, id: Int) {
                                dialog.cancel()
                            }
                        })
                var alert = build.create()
                alert.show()


            }
            R.id.aboutus -> {
                var start3 = Intent(this@Home, about_us::class.java)
                startActivity(start3)

            }
        }
        return false
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (mtoogle!!.onOptionsItemSelected(item)) {
            return true
        }
        var id = item!!.itemId
        when (id) {
            R.id.nonveg -> {
                var bundle = Bundle()
                bundle?.putString("val", phoneNO)
                bundle?.putString("posi", "1")
                Toast.makeText(this, "Non veg", Toast.LENGTH_SHORT).show()
                var start = Intent(this@Home, Category_item::class.java)
                start.putExtra("buni", bundle)
                startActivity(start)
            }
            R.id.veg -> {
                var bundle = Bundle()
                bundle?.putString("val", phoneNO)
                bundle?.putString("posi", "2")
                Toast.makeText(this, "veg", Toast.LENGTH_SHORT).show()
                var start = Intent(this@Home, Category_item::class.java)
                start.putExtra("buni", bundle)
                startActivity(start)
            }
            R.id.drink -> {
                var bundle = Bundle()
                bundle?.putString("val", phoneNO)
                bundle?.putString("posi", "3")
                Toast.makeText(this, "drinks", Toast.LENGTH_SHORT).show()
                var start = Intent(this@Home, Category_item::class.java)
                start.putExtra("buni", bundle)
                startActivity(start)
            }
            R.id.sweets -> {
                var bundle = Bundle()
                bundle?.putString("val", phoneNO)
                bundle?.putString("posi", "4")
                Toast.makeText(this, "sweets", Toast.LENGTH_SHORT).show()
                var start = Intent(this@Home, Category_item::class.java)
                start.putExtra("buni", bundle)
                startActivity(start)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        if (mdrawerlayout!!.isDrawerOpen(GravityCompat.START)) {
            mdrawerlayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    fun clickhandler() {
        image1?.setOnClickListener({

            val bundle = Bundle()
            bundle.putString("posi", "1")
            bundle.putString("userid", phoneNO)
            var start = Intent(this, viewspecialDish::class.java)
            start.putExtra("val", bundle)
            startActivity(start)
        })
        image2?.setOnClickListener(
                {

                    val bundle = Bundle()
                    bundle.putString("posi", "2")
                    bundle.putString("userid", phoneNO)
                    var start = Intent(this, viewspecialDish::class.java)
                    start.putExtra("val", bundle)
                    startActivity(start)
                }
        )
        image3?.setOnClickListener(
                {

                    val bundle = Bundle()
                    bundle.putString("posi", "3")
                    bundle.putString("userid", phoneNO)
                    var start = Intent(this, viewspecialDish::class.java)
                    start.putExtra("val", bundle)
                    startActivity(start)
                }
        )
        image4?.setOnClickListener(
                {

                    val bundle = Bundle()
                    bundle.putString("posi", "4")
                    bundle.putString("userid", phoneNO)
                    var start = Intent(this, viewspecialDish::class.java)
                    start.putExtra("val", bundle)
                    startActivity(start)
                }
        )
        image5?.setOnClickListener(
                {

                    val bundle = Bundle()
                    bundle.putString("posi", "5")
                    bundle.putString("userid", phoneNO)
                    var start = Intent(this, viewspecialDish::class.java)
                    start.putExtra("val", bundle)
                    startActivity(start)
                }
        )


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.category, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
