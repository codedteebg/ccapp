package com.babbangona.barcodescannerproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.babbangona.barcodescannerproject.model.inventoryT;

import java.util.ArrayList;
import java.util.List;

public class InventoryDateAdapter extends RecyclerView.Adapter<InventoryDateAdapter.MyViewHolder> implements Filterable {
    private List<inventoryT> hsfList;
    private List<inventoryT> hsfListFiltered;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView hsf, fieldID, bagsMarketed, seedType, dateProcessed, CCOID;

        public MyViewHolder(View view){
            super(view);

            hsf = view.findViewById(R.id.idLabel);
            fieldID = view.findViewById(R.id.FieldIDLabel);
            bagsMarketed = view.findViewById(R.id.bagsMarketedLabel);
            seedType = view.findViewById(R.id.seedTypeLabel);
            dateProcessed = view.findViewById(R.id.dateProcessedLabel);
            CCOID = view.findViewById(R.id.processedByLabel);
        }


    }

    public InventoryDateAdapter(List<inventoryT> hsfList){
        this.hsfList = hsfList;
        this.hsfListFiltered = hsfList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final inventoryT inventoryT = hsfListFiltered.get(position);
        holder.hsf.setText(inventoryT.getHSFID());
        holder.fieldID.setText(inventoryT.getFieldID());
        holder.bagsMarketed.setText(String.valueOf(inventoryT.getBagsMarketed()));
        holder.seedType.setText(inventoryT.getSeedType());
        holder.dateProcessed.setText(inventoryT.getDateProcessed());
        holder.CCOID.setText(inventoryT.getCCOID());
    }

    @Override
    public int getItemCount(){
        return hsfListFiltered.size();
    }

    public inventoryT getItem (int position){
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
                    List<inventoryT> filteredList = new ArrayList<>();
                    for (inventoryT row : hsfList){
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
                hsfListFiltered = (ArrayList<inventoryT>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}


