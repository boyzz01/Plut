package com.ardeveloper.plut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ardeveloper.plut.R;
import com.ardeveloper.plut.data.db.Product;
import com.ardeveloper.plut.data.db.UMKM;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductUmkmAdapter extends RecyclerView.Adapter<ProductUmkmAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private UMKM umkm;
    private List<Product> productList;
    private List<Product> itemListFiltered;
    private ItemAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, kodeTxt,stockTxt,hargaTxt;
        public CircleImageView imageView;


        public ConstraintLayout baseView;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.namaTxt);
            kodeTxt = view.findViewById(R.id.kodeTxt);
            hargaTxt = view.findViewById(R.id.hargaTxt);
            stockTxt = view.findViewById(R.id.stockTxt);
            imageView = view.findViewById(R.id.imgProduct);
            baseView = view.findViewById(R.id.baseView);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected Product in callback
                    listener.onItemSelected(itemListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public ProductUmkmAdapter(Context context, List<Product> productList, ItemAdapterListener listener, UMKM umkm) {
        this.context = context;
        this.listener = listener;
        this.productList = productList;
        this.itemListFiltered = productList;
        this.umkm = umkm;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produk_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Product product = itemListFiltered.get(position);
        holder.name.setText(product.getNama());
        holder.kodeTxt.setText("Kode : "+product.kodeProduk);
        holder.hargaTxt.setText("Harga : "+product.harga);
        holder.stockTxt.setText("Stock : "+product.stock);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ProductDetail.class);
//                intent.putExtra("umkm",umkm.getNama());
//                intent.putExtra("produk",product);
//                context.startActivity(intent);
//            }
//        });
        Glide.with(holder.itemView)
                .load(product.foto)
                    .error(R.drawable.imgproduct)
                .placeholder(R.drawable.imgproduct)
                .fitCenter()
                .into(holder.imageView);


//        if (position%2==1){
//            holder.baseView.setBackgroundColor(Color.parseColor("#F1F1F1"));
//        }

    }

    @Override
    public int getItemCount() {
        return itemListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemListFiltered = productList;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product row : productList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNama().toLowerCase().contains(charString.toLowerCase()) || row.getKodeProduk().toString().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    itemListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemListFiltered = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ItemAdapterListener {
        void onItemSelected(Product product);
    }
}