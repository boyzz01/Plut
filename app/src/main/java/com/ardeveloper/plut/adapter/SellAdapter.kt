package com.ardeveloper.plut.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.R
import com.ardeveloper.plut.data.model.Cart
import com.ardeveloper.plut.utils.Helper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners



class SellAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Cart>()
    private val tempList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_row_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val product = listProduct[position]
            holder.bindData(product, position)
        }
    }

    fun updateItem(cart: Cart, position: Int){
        listProduct[position] = cart
        notifyItemChanged(position)
    }

    fun deleteItem(position: Int){
        listProduct.removeAt(position)
        tempList.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(cart: Cart){

        val pos = tempList.indexOf(cart.product?.kodeProduk)
        if(pos > -1){
            listProduct[pos] = cart
            tempList[pos] = cart.product?.kodeProduk!!
            notifyItemChanged(pos)
        }
        else{
            listProduct.add(cart)
            tempList.add(cart.product?.kodeProduk!!)
            notifyItemInserted(itemCount-1)
        }

    }

    fun setItems(listProduct: List<Cart>?) {
        //this.listProduct.clear()
        val lastCount = itemCount
        listProduct?.let {
            it.forEach {cart->
                this.listProduct.add(cart)
                tempList.add(cart.product?.kodeProduk!!)
            }

        }
        notifyItemRangeInserted(lastCount,listProduct!!.size)
    }

    fun clearAdapter(){
        listProduct.clear()
        tempList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.findViewById<TextView>(R.id.tv_name)
        private val priceTv = view.findViewById<TextView>(R.id.tv_price)
        private val stockTv = view.findViewById<TextView>(R.id.tv_stok)
        private val imageIv = view.findViewById<ImageView>(R.id.iv_photo)
        private val countLayout = view.findViewById<LinearLayout>(R.id.ll_count)
        private val countTv = view.findViewById<TextView>(R.id.tv_count)
        private val decreaseBtn = view.findViewById<Button>(R.id.btn_minus)
        private val increaseBtn = view.findViewById<Button>(R.id.btn_plus)
        private val deleteBtn = view.findViewById<Button>(R.id.btn_delete)
        private val noteTv = view.findViewById<TextView>(R.id.tv_note)


        @SuppressLint("SetTextI18n")
        fun bindData(data: Cart, position: Int) {

            val product = data.product
            nameTv.text = "${product?.nama}"
            priceTv.text = "Rp."+Helper.convertToCurrency(product.harga.toString())
            val stock = product?.stock!!.toDouble()
            countTv.text = Helper.convertToCurrency(data.count!!)
            noteTv.text = "${data.note}"

            if("0" == product.stock.toString()){
                stockTv.visibility = View.GONE
            }
            else{
                stockTv.visibility = View.VISIBLE
                stockTv.text = "Stok : ${Helper.convertToCurrency(stock!!)}"
            }

            if(product?.foto== null){
                Glide.with(itemView.context)
                    .load(R.drawable.imgproduct)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(imageIv)

            }
            else{
                Glide.with(itemView.context)
                    .load(product?.foto)
                    .error(R.drawable.imgproduct)
                    .placeholder(R.drawable.imgproduct)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(imageIv)
            }

            increaseBtn.setOnClickListener {
                callback?.onIncrease(data,position)
            }


            decreaseBtn.setOnClickListener {
                callback?.onDecrease(data,position)

            }

            deleteBtn.setOnClickListener {
                callback?.onDelete(data,position)
            }

            itemView.setOnClickListener {
                callback?.onNote(data,position)
            }

            countTv.setOnClickListener {
                callback?.onCountDialog(data,position)
            }


        }
    }

    var callback: ItemClickCallback?= null

    interface ItemClickCallback{
        fun onDecrease(data: Cart, position: Int)
        fun onIncrease(data: Cart, position: Int)
        fun onDelete(data: Cart, position: Int)
        fun onNote(data: Cart, position: Int)
        fun onCountDialog(data: Cart, position: Int)
    }
}