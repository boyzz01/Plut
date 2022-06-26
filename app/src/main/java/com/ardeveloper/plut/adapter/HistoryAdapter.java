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
import com.ardeveloper.plut.data.db.UMKM;
import com.ardeveloper.plut.data.response.Transaksi;
import com.ardeveloper.plut.utils.Helper;
import com.ardeveloper.plut.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Transaksi> transaksiList;
    private List<Transaksi> contactListFiltered;
    private transaksiAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id,total,tgl;

        public ConstraintLayout baseView;
        public MyViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.tv_id);
            total = view.findViewById(R.id.tv_total);
            tgl = view.findViewById(R.id.tv_tgl);

            baseView = view.findViewById(R.id.baseView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected Transaksi in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public HistoryAdapter(Context context, List<Transaksi> transaksiList, transaksiAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.transaksiList = transaksiList;
        this.contactListFiltered = transaksiList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaksi_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Transaksi transaksi = contactListFiltered.get(position);

        holder.total.setText("Nilai Transaksi : "+ Helper.INSTANCE.convertToCurrency(transaksi.getTotalHarga()));
        holder.id.setText("Nota : "+transaksi.getIdTransaksi());
        holder.tgl.setText("Tanggal : "+transaksi.getCreatedAt());
//        holder.name.setText(transaksi.getNama());
//        holder.kodeTxt.setText("Kode Transaksi : "+transaksi.getKodetransaksi());
//        holder.nibTxt.setText("NIB : "+transaksi.getNib());

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
                    contactListFiltered = transaksiList;
                } else {
                    List<Transaksi> filteredList = new ArrayList<>();
                    for (Transaksi row : transaksiList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getIdTransaksi().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<Transaksi>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface transaksiAdapterListener {
        void onContactSelected(Transaksi transaksi);
    }
}