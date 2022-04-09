package com.ardeveloper.plut.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivityMainBinding
import com.ardeveloper.plut.preferences.SharedPrefs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private var bottomNavigationView: BottomNavigationView? = null
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()

    }

    private fun initView() {
//        setSupportActionBar(binding.toolbar)
//        binding.toolbar.title = "Dashboard"
//        binding.regUmkm.setOnClickListener{
//            val intent = Intent(this, RegisterUmkm::class.java)
//            startActivity(intent)
//        }
//
//        binding.umkmView.setOnClickListener{
//            val intent = Intent(this, Umkm::class.java)
//            startActivity(intent)
//        }
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val posView = findViewById<CardView>(R.id.posView)
        posView.setOnClickListener{
            val intent = Intent(this, Kasir::class.java)
            startActivity(intent)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        bottomNavigationView = findViewById(R.id.navigation)

//
        //
        val layoutParams: CoordinatorLayout.LayoutParams =
            bottomNavigationView!!.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.setBehavior(BottomNavigationBehavior())

        bottomNavigationView!!.setSelectedItemId(R.id.navigationHome)

        //handling floating action menu

        //handling floating action menu
        val fab = findViewById(R.id.floatingActionButton) as FloatingActionButton
       fab.setOnClickListener(View.OnClickListener {
            val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
            drawer.openDrawer(GravityCompat.START)
        })
    }

    override fun onStart() {

//        val intent = Intent(this, RegisterUmkm::class.java)
//        startActivity(intent)
//        finish()

        if (!SharedPrefs.getBoolean(this,SharedPrefs.LOGIN)){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        super.onStart()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment
        when (item.itemId) {
            R.id.navigationMyProfile -> return true
            R.id.navigationMyCourses -> return true
            R.id.navigationHome -> return true
            R.id.navigationSearch -> return true
            R.id.navigationMenu -> {
                val drawer: DrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
                drawer.openDrawer(GravityCompat.START)
                return true
            }
        }
        return false
    }


}