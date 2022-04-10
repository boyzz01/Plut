package com.ardeveloper.plut.view

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.databinding.ActivityMainBinding
import com.ardeveloper.plut.preferences.SharedPrefs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.integration.android.IntentIntegrator
import es.dmoral.toasty.Toasty

class MainActivity : BaseActivity() {

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
            val intent = Intent(this,Umkm::class.java)
            startActivity(intent)
        }

        val regView = findViewById<CardView>(R.id.regView)
        regView.setOnClickListener{
            val intent = Intent(this,RegisterUmkm::class.java)
            startActivity(intent)
        }

        val transaksiView = findViewById<CardView>(R.id.transaksiView)
        transaksiView.setOnClickListener{
            val intent = Intent(this,AllTransaksi::class.java)
            startActivity(intent)
        }
        val laporanView = findViewById<CardView>(R.id.laporanView)
        laporanView.setOnClickListener{
            val intent = Intent(this,Laporan::class.java)
            startActivity(intent)
        }


//        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
//        val toggle = ActionBarDrawerToggle(
//            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
//        )
//        drawer.addDrawerListener(toggle)
//        toggle.syncState()

//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        navigationView.setNavigationItemSelectedListener(this)

        bottomNavigationView = findViewById(R.id.navigation)

//
        //
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
                R.id.navigation_produk -> {
                    val intent = Intent(this, AllProduk::class.java)
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



    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toasty.info(this, "Tekan Lagi Untuk Keluar").show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
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
}