package com.ardeveloper.plut.adapter

import android.content.Context
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.R
import com.ardeveloper.plut.data.response.ResponseProduk
import com.ardeveloper.plut.utils.Helper
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty
import java.util.*
import kotlin.collections.ArrayList


class KeranjangAdapter(
    private val context: Context,
    private val productList: List<ResponseProduk>,
    private val listenerClick: cartListener,
) : RecyclerView.Adapter<KeranjangAdapter.MyViewHolder>(), Filterable {
    private var listFiltered: List<ResponseProduk>
    private  var tombol : Boolean = false
    private var timer: Timer? = null
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        public val nameTv = view.findViewById<TextView>(R.id.tv_name)
        public val priceTv = view.findViewById<TextView>(R.id.tv_price)
        public val stockTv = view.findViewById<TextView>(R.id.tv_stok)
        public val imageIv = view.findViewById<ImageView>(R.id.iv_photo)
    //    public val btnAddToCart = view.findViewById<Button>(R.id.btn_add_cart)
        public val plus = view.findViewById<ImageView>(R.id.btn_plus)
        public val minus = view.findViewById<ImageView>(R.id.btn_minus)
        public val etJumlah = view.findViewById<EditText>(R.id.tv_count)
        public val btnDel = view.findViewById<Button>(R.id.btn_delete)
//        public val viewBtn = view.findViewById<MaterialRippleLayout>(R.id.viewBtn)
//        public val viewNumber = view.findViewById<LinearLayout>(R.id.viewNumber)


//        public val infoTv = view.findViewById<TextView>(R.id.tv_info)
        //public val wrapper = view.findViewById<LinearLayout>(R.id.ll_wrapper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_row_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = listFiltered[position]


        Glide.with(holder.imageIv)
            .load(product.foto)
            .error(R.drawable.imgproduct)
            .placeholder(R.drawable.imgproduct)
            .fitCenter()
            .into(holder.imageIv)


        holder.nameTv.text = product.nama
        holder.priceTv.text = "Rp. "+ Helper.convertToCurrency(product.harga.toString())
        holder.stockTv.text = "Stok : "+product.stock.toString()

        holder.etJumlah.setText(""+product.jumlah)

        holder.plus.setOnClickListener {
            var jumlah = holder.etJumlah.text.toString().toInt()+1
            //   holder.etJumlah.setText(""+(jumlah))
            if (jumlah<=product.stock){
                listenerClick.onCartClick(product.kodeProduk,jumlah)
            }

        }

        holder.minus.setOnClickListener {
            var jumlah = holder.etJumlah.text.toString().toInt()-1
            //  holder.etJumlah.setText(""+(jumlah) )
            listenerClick.onCartClick(product.kodeProduk,jumlah)
            tombol = true
        }

        holder.btnDel.setOnClickListener {
            val alert= AlertDialog.Builder(context)
            alert.setTitle("Konfirmasi");
            alert.setMessage("Anda yakin ingin menghapus produk dari keranjang?");
            alert.setPositiveButton(
                "Ya"
            ) { dialog, which ->
                listenerClick.onCartClick(product.kodeProduk,0)
                dialog.dismiss()
            }

            alert.setNegativeButton(
                "Tidak"
            ) { dialog, which -> dialog.dismiss() }

            alert.show()
        }
        holder.etJumlah.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(holder.etJumlah.hasFocus()) {
                    //jika kosong

                }
            }

            override fun afterTextChanged(p0: Editable?) {

                Handler().postDelayed({
                    if(holder.etJumlah.hasFocus()){
                        if (p0.toString().isNotEmpty())
                        {
                            if (p0.toString().toInt()>product.stock){
                                holder.etJumlah.setText(product.stock.toString())
                                Toasty.error(context,"Melebihi Stock",Toast.LENGTH_SHORT).show()
                            }else{
                                if (p0.toString().toInt()>=1){
                                    Log.d("response3",p0.toString())
                                    listenerClick.onCartClick(product.kodeProduk,p0.toString().toInt())

                                }else{
                                    Log.d("response2",p0.toString())
                                    listenerClick.onCartClick(product.kodeProduk,0)


                                }
                                holder.etJumlah.clearFocus()
                            }
//
                            //
                        }else{
                            holder.etJumlah.setText("0")
                        }

                    }


                }, 800) // 3000 is the delayed time in milliseconds.
//


                //

            }

        })

    }

    override fun getItemCount(): Int {
        return listFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                listFiltered = if (charString.isEmpty()) {
                    productList
                } else {
                    val filteredList: MutableList<ResponseProduk> = ArrayList()
                    for (row in productList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNama().toLowerCase()
                                .contains(charString.toLowerCase()) || row.getId().toString()
                                .contains(charSequence)
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = listFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                listFiltered = filterResults.values as ArrayList<ResponseProduk>
                notifyDataSetChanged()
            }
        }
    }

    interface ProductAdapterListener {
        fun onItemSelected(product: ResponseProduk?)
    }

//    interface getDataListener{
//        fun addTotal(jumlah: Int, harga: Int)
//    }

    interface refreshListener{
        fun refreshData()
    }

    interface cartListener{
        fun onCartClick(kode: String,jumlah:Int)
    }

    interface addBadgeListener{
        fun addBadge(type : Int)
    }
    init {
        listFiltered = productList
    }


}