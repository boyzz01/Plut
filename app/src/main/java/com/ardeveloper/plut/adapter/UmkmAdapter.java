package com.ardeveloper.plut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ardeveloper.plut.R;
import com.ardeveloper.plut.data.db.UMKM;
import com.ardeveloper.plut.preferences.SharedPrefs;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UmkmAdapter extends RecyclerView.Adapter<UmkmAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<UMKM> umkmList;
    private List<UMKM> contactListFiltered;
    private UmkmAdapterListener listener;
    private deleteListener deletelistener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, kodeTxt,nibTxt;
        public CircleImageView imageView;
        public ImageView delete;

        public ConstraintLayout baseView;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.namaTxt);
            kodeTxt = view.findViewById(R.id.kodeTxt);
            nibTxt = view.findViewById(R.id.stockTxt);
            delete = view.findViewById(R.id.retur);

            imageView = view.findViewById(R.id.imgUmkm);
            baseView = view.findViewById(R.id.baseView);

            if (SharedPrefs.getInt(context,SharedPrefs.USER_LEVEL)==2){
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deletelistener.onDelete(contactListFiltered.get(getAdapterPosition()));
                    }
                });
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected UMKM in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public UmkmAdapter(Context context, List<UMKM> umkmList, UmkmAdapterListener listener,deleteListener deleteListener) {
        this.context = context;
        this.listener = listener;
        this.umkmList = umkmList;
        this.contactListFiltered = umkmList;
        this.deletelistener = deleteListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.umkm_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final UMKM Umkm = contactListFiltered.get(position);
        holder.name.setText(Umkm.getNama());
        holder.kodeTxt.setText("Kode UMKM : "+Umkm.getKodeUmkm());
        holder.nibTxt.setText("NIB : "+Umkm.getNib());


        Glide.with(holder.imageView)
                .load(Umkm.getFoto())
                .error(R.drawable.shop)
                .placeholder(R.drawable.shop)
                .fitCenter()
                .into(holder.imageView);

//        if (position%2==1){
//            holder.baseView.setBackgroundColor(Color.parseColor("#F1F1F1"));
//        }

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = umkmList;
                } else {
                    List<UMKM> filteredList = new ArrayList<>();
                    for (UMKM row : umkmList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNama().toLowerCase().contains(charString.toLowerCase()) || row.getId().toString().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<UMKM>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface deleteListener{
        void onDelete(UMKM umkm);
    }
    public interface UmkmAdapterListener {
        void onContactSelected(UMKM Umkm);
    }
}