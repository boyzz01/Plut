package com.ardeveloper.plut.view

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivityMainBinding
import com.ardeveloper.plut.preferences.SharedPrefs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import es.dmoral.toasty.Toasty


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var bottomNavigationView: BottomNavigationView? = null
    private var barcodeChoosedView = 0
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()

    }

    @SuppressLint("MissingPermission")
    private fun initView() {

        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        mBluetoothAdapter.enable()
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)



        val posView = findViewById<CardView>(R.id.posView)
        posView.setOnClickListener{
            val intent = Intent(this, Kasir::class.java)
            startActivity(intent)
        }

        val umkmView = findViewById<CardView>(R.id.umkm)
        umkmView.setOnClickListener{
            val intent = Intent(this,Inventory::class.java)
            startActivity(intent)
        }

        val regView = findViewById<CardView>(R.id.regView)
        regView.setOnClickListener{
            val intent = Intent(this,RegisterUmkm::class.java)
            startActivity(intent)
        }

        val transaksiView = findViewById<CardView>(R.id.reportView)
        transaksiView.setOnClickListener{
            val intent = Intent(this,Report::class.java)
            startActivity(intent)
        }
        val laporanView = findViewById<CardView>(R.id.laporanView)
        laporanView.setOnClickListener{
            val intent = Intent(this,Laporan::class.java)
            startActivity(intent)
        }

        val username = SharedPrefs.getString(this,SharedPrefs.USERNAME)

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val userTxt = headerView.findViewById<TextView>(R.id.usernameTxt)
        userTxt.text = username
        navigationView.setNavigationItemSelectedListener(this)

        val all = findViewById<CardView>(R.id.allProduk)
        all.setOnClickListener {
            val intent = Intent(this, AllProduk::class.java)
            startActivity(intent)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()



        val imgNav = findViewById<ImageView>(R.id.imgNav)
        imgNav.setOnClickListener(View.OnClickListener {
            val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
            drawer.openDrawer(GravityCompat.START)
        })



        bottomNavigationView = findViewById(R.id.navigation)


        val layoutParams: CoordinatorLayout.LayoutParams =
            bottomNavigationView!!.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationBehavior()


        bottomNavigationView!!.selectedItemId = R.id.navigation_home
        bottomNavigationView!!.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_account -> {
                    val intent = Intent(this, Akun::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_home -> return@setOnItemSelectedListener true
                R.id.navigation_scan ->{

                    barcodeChoosedView = R.id.navigation_scan
                    val ii = IntentIntegrator(this).setCaptureActivity(
                        ScannerActivity::class.java
                    )
                    ii.setOrientationLocked(false);
                    ii.initiateScan()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }

    override fun onResume() {
        bottomNavigationView!!.selectedItemId = R.id.navigation_home
        super.onResume()

    }
    override fun onStart() {


        if (!SharedPrefs.getBoolean(this,SharedPrefs.LOGIN) || SharedPrefs.getString(this,SharedPrefs.USERID).equals("") || SharedPrefs.getInt(this,SharedPrefs.USER_LEVEL).toString() == ""){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        super.onStart()
    }



    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toasty.info(this, "Tekan Lagi Untuk Keluar").show()

            Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result.contents == null) {
            return
        }else{
            var dataQr = result.contents
            //dataQr = dataQr.replace("\\s+".toRegex(), "")
            //dataQr = dataQr.substring(0,12)
            Log.d("tessqr",dataQr)
            val intent = Intent(this, ProductDetail::class.java)
            intent.putExtra("produk", dataQr)
            startActivity(intent)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.pos) {
            val intent = Intent(this, Kasir::class.java)
            startActivity(intent)
        } else if (id == R.id.register) {
            val intent = Intent(this,RegisterUmkm::class.java)
            startActivity(intent)
        } else if (id == R.id.inventory) {
            val intent = Intent(this,Umkm::class.java)
            startActivity(intent)
        } else if (id == R.id.report) {
            val intent = Intent(this,Report::class.java)
            startActivity(intent)
        }  else if (id == R.id.shift) {
            val intent = Intent(this,Shift::class.java)
            startActivity(intent)
        }  else if (id == R.id.setting) {
            val intent = Intent(this,Setting::class.java)
            startActivity(intent)
        } else if (id == R.id.keluar) {
            SharedPrefs.save(this, SharedPrefs.LOGIN,false)
            SharedPrefs.save(this, SharedPrefs.USERNAME,"")
            SharedPrefs.save(this, SharedPrefs.USER_LEVEL,"")
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}