package com.babbangona.barcodescannerproject;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.babbangona.barcodescannerproject.model.hsf;
import com.babbangona.barcodescannerproject.model.hsfTransportT;

import java.util.ArrayList;
import java.util.List;

public class HSFTransportAdapter extends RecyclerView.Adapter<HSFTransportAdapter.MyViewHolder> implements Filterable {
    private List<hsf> hsfList;
    private List<hsf> hsfListFiltered;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView hsf, fieldID, bagsMarketed, transporterID, amount;

        public MyViewHolder(View view){
            super(view);

            hsf = view.findViewById(R.id.hsfText);
            fieldID = view.findViewById(R.id.fieldText);
            bagsMarketed = view.findViewById(R.id.bagsText);
            transporterID = view.findViewById(R.id.transporterText);
            amount = view.findViewById(R.id.amountText);
        }


    }

    public HSFTransportAdapter (List<hsf> hsfList){
        this.hsfList = hsfList;
        this.hsfListFiltered = hsfList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hsf_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final hsf hsfTransportT = hsfListFiltered.get(position);
        holder.hsf.setText(hsfTransportT.getHSFID());
        holder.fieldID.setText(hsfTransportT.getFieldID());
        holder.bagsMarketed.setText(String.valueOf(hsfTransportT.getBagsMarketed()));
        holder.transporterID.setText(hsfTransportT.getTransporterID());
        holder.amount.setText(String.valueOf((hsfTransportT.getBagsRate() * hsfTransportT.getBagsMarketed())));
    }

    @Override
    public int getItemCount(){
        return hsfListFiltered.size();
    }

    public hsf getItem (int position){
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
                    List<hsf> filteredList = new ArrayList<>();
                    for (hsf row : hsfList){
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
                hsfListFiltered = (ArrayList<hsf>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}


