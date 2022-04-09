package com.ardeveloper.plut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ardeveloper.plut.R;
import com.ardeveloper.plut.data.db.UMKM;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<UMKM> umkmList;
    private List<UMKM> contactListFiltered;
    private UmkmAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, kodeTxt,nibTxt;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.nama);
            kodeTxt = view.findViewById(R.id.kodeTxt);
            nibTxt = view.findViewById(R.id.nibTxt);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected UMKM in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public CartAdapter(Context context, List<UMKM> umkmList, UmkmAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.umkmList = umkmList;
        this.contactListFiltered = umkmList;
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
        holder.kodeTxt.setText(""+Umkm.getId());
        holder.nibTxt.setText(""+Umkm.getNib());

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

    public interface UmkmAdapterListener {
        void onContactSelected(UMKM Umkm);
    }
}