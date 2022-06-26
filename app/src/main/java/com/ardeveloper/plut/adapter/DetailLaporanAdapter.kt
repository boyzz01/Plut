package com.ardeveloper.plut.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.R
import com.ardeveloper.plut.data.response.ResponseCart
import com.ardeveloper.plut.data.response.ResponseProduk
import com.ardeveloper.plut.data.response.TransaksiItem
import com.ardeveloper.plut.utils.Helper
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView


class DetailLaporanAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<TransaksiItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.laporan_row_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val product = listProduct[position]
            holder.bindData(product)
        }
    }

    fun setItems(listProduct: List<TransaksiItem>?) {
        //this.listProduct.clear()
        listProduct?.let {
            this.listProduct.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clearAdapter(){
        listProduct.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        private val nameTv = view.findViewById<TextView>(R.id.tv_id)
        private val countTv = view.findViewById<TextView>(R.id.tv_total)
        private val priceTv = view.findViewById<TextView>(R.id.tv_tgl)
        private val subtotalTv = view.findViewById<TextView>(R.id.tv_status)
        private val img = view.findViewById<ImageView>(R.id.iv_photo)

        @SuppressLint("SetTextI18n")
        fun bindData(data: TransaksiItem) {

            val count = data.totalProduk.toDouble()
            val price = data.totalHarga/data.totalProduk
            nameTv.text = "ID Transaksi : "+data.idTransaksi
            countTv.text = "${Helper.convertToCurrency(count)} x @${Helper.convertToCurrency(price)}"

            priceTv.text = "Tanggal : "+data.createdAt
            val subtotal = count * price
            subtotalTv.text ="Rp.${Helper.convertToCurrency(data.totalHarga)}"

            Glide.with(img)
                .load(data.foto)
                .error(R.drawable.imgproduct)
                .placeholder(R.drawable.imgproduct)
                .fitCenter()
                .into(img)
        }
    }
}