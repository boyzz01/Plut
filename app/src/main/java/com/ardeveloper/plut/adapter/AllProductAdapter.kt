package com.ardeveloper.plut.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ardeveloper.plut.R
import com.ardeveloper.plut.data.db.Product
import com.ardeveloper.plut.data.service.AllDbService
import com.ardeveloper.plut.view.ProductDetail
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty


class AllProductAdapter(
    private val context: Context,
    private val productList: List<Product>,
    private val listener: ProductAdapterListener
) : RecyclerView.Adapter<AllProductAdapter.MyViewHolder>(), Filterable {
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
            .inflate(R.layout.all_product_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = ListFiltered[position]
        holder.nameTv.text = product.nama
        holder.priceTv.text = "Rp. "+product.harga
        holder.stockTv.text = "Stok : "+product.stock.toString()

        holder.btnAddToCart.setOnClickListener {
            val intent = Intent(context, ProductDetail::class.java)
            intent.putExtra("produk", product.kodeProduk)
            context.startActivity(intent)
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ProductDetail.class);
//                intent.putExtra("umkm",umkm.getNama());
//                intent.putExtra("produk",product);
//                context.startActivity(intent);
//            }
//        });
        Glide.with(holder.imageIv)
            .load(product.foto)
            .error(R.drawable.imgproduct)
            .placeholder(R.drawable.imgproduct)
            .fitCenter()
            .into(holder.imageIv)

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