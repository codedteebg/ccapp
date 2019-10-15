package com.babbangona.barcodescannerproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.babbangona.barcodescannerproject.model.hsfTransportT;

import java.util.ArrayList;
import java.util.List;


public class TransportPaidAdapter extends RecyclerView.Adapter<TransportPaidAdapter.MyViewHolder> implements Filterable {
    private List<hsfTransportT> hsfList;
    private List<hsfTransportT> hsfListFiltered;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView hsf, fieldID, datePaid, transporterID, amount, msa;

        public MyViewHolder(View view){
            super(view);

            hsf = view.findViewById(R.id.hsfTransText);
            fieldID = view.findViewById(R.id.fieldTransText);
            datePaid = view.findViewById(R.id.dateTransText);
            transporterID = view.findViewById(R.id.transporterTransText);
            amount = view.findViewById(R.id.amountTransText);
            msa = view.findViewById(R.id.msaTransText);
        }


    }

    public TransportPaidAdapter (List<hsfTransportT> hsfList){
        this.hsfList = hsfList;
        this.hsfListFiltered = hsfList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transportpaid_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final hsfTransportT hsfTransportT = hsfListFiltered.get(position);
        holder.hsf.setText(hsfTransportT.getHSFID());
        holder.fieldID.setText(hsfTransportT.getFieldID());
        holder.datePaid.setText(String.valueOf(hsfTransportT.getDatePaid()));
        holder.transporterID.setText(hsfTransportT.getTransporterID());
        holder.amount.setText(String.valueOf((hsfTransportT.getAmountPaid())));
        holder.msa.setText(hsfTransportT.getMSAID());
    }

    @Override
    public int getItemCount(){
        return hsfListFiltered.size();
    }

    public hsfTransportT getItem (int position){
        return hsfListFiltered.get(position);
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    hsfListFiltered = hsfList;
                } else{
                    List<hsfTransportT> filteredList = new ArrayList<>();
                    for (hsfTransportT row : hsfList){
                        //checking for name or staffID match
                        if(row.getHSFID().toLowerCase().contains(charString.toLowerCase()) || row.getFieldID().toLowerCase().contains((charString.toLowerCase()))){
                            filteredList.add(row);
                        }
                    }
                    hsfListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = hsfListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                hsfListFiltered = (ArrayList<hsfTransportT>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
