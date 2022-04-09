package com.ardeveloper.plut.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.R
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.data.service.AllDbService
import es.dmoral.toasty.Toasty


class PosProductAdapter(
    private val context: Context,
    private val productList: List<Product>,
    private val listener: ProductAdapterListener
) : RecyclerView.Adapter<PosProductAdapter.MyViewHolder>(), Filterable {
    private var ListFiltered: List<Product>

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        public val nameTv = view.findViewById<TextView>(R.id.txt_name)
        public val priceTv = view.findViewById<TextView>(R.id.txt_harga)
        public val stockTv = view.findViewById<TextView>(R.id.txt_stok)
        public val imageIv = view.findViewById<ImageView>(R.id.img_product)
        public val btnAddToCart = view.findViewById<Button>(R.id.btn_add_cart)
//        public val infoTv = view.findViewById<TextView>(R.id.tv_info)
        //public val wrapper = view.findViewById<LinearLayout>(R.id.ll_wrapper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pos_product_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = ListFiltered[position]
        holder.nameTv.text = product.nama
        holder.priceTv.text = "Rp. "+product.harga
        holder.stockTv.text = "Stok : "+product.stock.toString()

        holder.btnAddToCart.setOnClickListener {
            val dbService : AllDbService
            val activity = context as Activity
            dbService = AllDbService(context)

            val check = dbService.addTocart(product.kodeProduk.toString())

            if (check == 1) {
                Toasty.success(context, "Produk ditambahkan Kekeranjang", Toast.LENGTH_SHORT).show()

            } else if (check == 2) {
                Toasty.info(context, "Produk Sudah Ada di Keranjang", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toasty.error(
                    context,
                    "Gagal Menambah Produk",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return ListFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                ListFiltered = if (charString.isEmpty()) {
                    productList
                } else {
                    val filteredList: MutableList<Product> = ArrayList()
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
                filterResults.values = ListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                ListFiltered = filterResults.values as ArrayList<Product>
                notifyDataSetChanged()
            }
        }
    }

    interface ProductAdapterListener {
        fun onItemSelected(product: Product?)
    }

    init {
        ListFiltered = productList
    }
}