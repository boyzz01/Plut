package com.ardeveloper.plut.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.R
import com.ardeveloper.plut.data.response.ResponseCart
import com.ardeveloper.plut.data.response.ResponseProduk
import com.ardeveloper.plut.utils.Helper



class ConfirmationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<ResponseProduk>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_sell_transaction, parent, false))
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

    fun setItems(listProduct: List<ResponseProduk>?) {
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


        private val nameTv = view.findViewById<TextView>(R.id.tv_name)
        private val countTv = view.findViewById<TextView>(R.id.tv_count)
        private val priceTv = view.findViewById<TextView>(R.id.tv_price)
        private val subtotalTv = view.findViewById<TextView>(R.id.tv_sell_subtotal)

        @SuppressLint("SetTextI18n")
        fun bindData(data: ResponseProduk) {

            val count = data.jumlah.toDouble()
            val price = data.harga.toDouble()
            nameTv.text = data.nama
            countTv.text = "${Helper.convertToCurrency(count)}x"
            priceTv.text = "@${Helper.convertToCurrency(price)}"
            val subtotal = count * price
            subtotalTv.text ="Rp.${Helper.convertToCurrency(subtotal)}"
        }
    }
}