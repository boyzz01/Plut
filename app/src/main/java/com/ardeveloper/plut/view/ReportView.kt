package com.ardeveloper.plut.view

import ApiService
import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Base64.decode
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import android.webkit.WebSettings.PluginState
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.BaseActivity
import com.ardeveloper.plut.R
import com.ardeveloper.plut.adapter.ReportAdapter
import com.ardeveloper.plut.api.ApiClient
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.databinding.ActivitySalesFastMoving3Binding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.util.*


class ReportView : BaseActivity(), ReportAdapter.ItemAdapterListener {
    lateinit var downloadListener: DownloadListener
    private lateinit var b : ActivitySalesFastMoving3Binding
    private lateinit var adapter : ReportAdapter
    private lateinit var productList : MutableList<Product>
    var judul =""
    var report = ""
    private lateinit var apiInterface: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySalesFastMoving3Binding.inflate(layoutInflater)
        setContentView(b.root)

        initView()

        b.wv.settings.javaScriptEnabled = true





        b.wv.settings.javaScriptEnabled = true
        b.wv.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            b.wv.loadUrl(
                JavaScriptInterface.getBase64StringFromBlobUrl(url)
            )
        })
        b.wv.settings
            .setAppCachePath(this
                .applicationContext.cacheDir.absolutePath
            )
        b.wv.settings.cacheMode = WebSettings.LOAD_DEFAULT
        b.wv.settings.databaseEnabled = true
        b.wv.settings.domStorageEnabled = true
        b.wv.settings.useWideViewPort = true
        b.wv.settings.loadWithOverviewMode = true
        b.wv.addJavascriptInterface(JavaScriptInterface(this), "Android")
        b.wv.settings.pluginState = PluginState.ON
        


   //     b.wv.loadUrl(https://www.google.co.id/)

        when(report){
            "1"->{
                b.wv.loadUrl("https://ardisahputra.me/plut/fast")
            }
            "2"->{
                b.wv.loadUrl("https://ardisahputra.me/plut/slow")
            }
            "3"->{
                b.wv.loadUrl("https://ardisahputra.me/plut/all")
            }
            "4"->{
                b.wv.loadUrl("https://ardisahputra.me/plut/umkm")
            }
            "5"->{
                b.wv.loadUrl("https://ardisahputra.me/plut/kota")
            }
            "6"->{
                b.wv.loadUrl("https://ardisahputra.me/plut/kategori")
            }
            "7"->{
                b.wv.loadUrl("https://ardisahputra.me/plut/stock_all")
            }
            "8"->{
                    b.wv.loadUrl("https://ardisahputra.me/plut/stock_umkm")
            }
            "9"->{
            b.wv.loadUrl("https://ardisahputra.me/plut/stock_kategori")
            }
            "10"->{
                b.wv.loadUrl("https://ardisahputra.me/plut/stock_outdate")
            }
            "11"->{
                b.wv.loadUrl("https://ardisahputra.me/plut/stock_zero")
            }


        }




    }
    var writeAccess = false


    class JavaScriptInterface(private val context: Context) {
        @JavascriptInterface
        @Throws(IOException::class)
        fun getBase64FromBlobData(base64Data: String) {
            convertBase64StringToPdfAndStoreIt(base64Data)
        }

        @Throws(IOException::class)
        private fun convertBase64StringToPdfAndStoreIt(base64PDf: String) {
            val notificationId = 1
            val currentDateTime: String = DateFormat.getDateTimeInstance().format(Date())
            val dwldsPath = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                ).toString() + "/YourFileName_" + currentDateTime + "_.xlsx"
            )


            val pdfAsBytes: ByteArray =
               decode(base64PDf.replaceFirst("data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,", ""), 0)

            Log.d("datatesbyte",pdfAsBytes.toString())
            val os: FileOutputStream
            os = FileOutputStream(dwldsPath, false)
            os.write(pdfAsBytes)
            os.flush()
            if (dwldsPath.exists()) {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                val apkURI = FileProvider.getUriForFile(
                    context,
                    context.applicationContext.packageName + ".provider",
                    dwldsPath
                )
                intent.setDataAndType(
                    apkURI,
                    MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx")
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val pendingIntent =
                    PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                val CHANNEL_ID = "MYCHANNEL"
                val notificationManager =
                    context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notificationChannel =
                        NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW)
                    val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
                        .setContentText("You have got something new!")
                        .setContentTitle("File downloaded")
                        .setContentIntent(pendingIntent)
                        .setChannelId(CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.sym_action_chat)
                        .build()
                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(notificationChannel)
                        notificationManager.notify(notificationId, notification)
                    }
                } else {
                    val b: NotificationCompat.Builder = NotificationCompat.Builder(
                        context,
                        CHANNEL_ID
                    )
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(android.R.drawable.sym_action_chat) //.setContentIntent(pendingIntent)
                        .setContentTitle("MY TITLE")
                        .setContentText("MY TEXT CONTENT")
                    if (notificationManager != null) {
                        notificationManager.notify(notificationId, b.build())
                        val h = Handler()
                        val delayInMilliseconds: Long = 1000
                        h.postDelayed(
                            Runnable { notificationManager.cancel(notificationId) },
                            delayInMilliseconds
                        )
                    }
                }
            }
            Toast.makeText(context, "PDF FILE DOWNLOADED!", Toast.LENGTH_SHORT).show()
        }

        companion object {
            fun getBase64StringFromBlobUrl(blobUrl: String): String {
                return if (blobUrl.startsWith("blob")) {
                    "javascript: var xhr = new XMLHttpRequest();" +
                            "xhr.open('GET', '" + blobUrl + "', true);" +
                            "xhr.setRequestHeader('Content-type','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');" +
                            "xhr.responseType = 'blob';" +
                            "xhr.onload = function(e) {" +
                            "    if (this.status == 200) {" +
                            "        var blobPdf = this.response;" +
                            "        var reader = new FileReader();" +
                            "        reader.readAsDataURL(blobPdf);" +
                            "        reader.onloadend = function() {" +
                            "            base64data = reader.result;" +
                            "            Android.getBase64FromBlobData(base64data);" +
                            "        }" +
                            "    }" +
                            "};" +
                            "xhr.send();"
                } else "javascript: console.log('It is not a Blob URL');"
            }
        }
    }


    private fun initView() {

        val intent = intent
        judul = intent.getStringExtra("judul").toString()
        report= intent.getStringExtra("report").toString()
        apiInterface = ApiClient.getClient(this).create(ApiService::class.java)
        setSupportActionBar(b.toolbar23)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = judul

        productList = ArrayList()
        adapter = ReportAdapter(this,productList,this)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_export, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return  super.onOptionsItemSelected(item)


    }

    override fun onItemSelected(product: Product?) {

    }







}