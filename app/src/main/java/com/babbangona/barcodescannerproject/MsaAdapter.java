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
import com.babbangona.barcodescannerproject.model.msaT;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MsaAdapter extends RecyclerView.Adapter<MsaAdapter.MyViewHolder> implements Filterable {
        private List<msaT> msaList;
        private List<msaT> msaListFiltered;
       // private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView msaStaff, msaName;

            public MyViewHolder(View view){
                super(view);
                msaStaff = view.findViewById(R.id.msaStaffID);
                msaName = view.findViewById(R.id.msaName);
            }
        }

        public MsaAdapter (List<msaT> msaList){
            this.msaList = msaList;
            this.msaListFiltered = msaList;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.msa_list_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position){
            msaT msaT = msaListFiltered.get(position);
            holder.msaStaff.setText(msaT.getStaff_id());
            holder.msaName.setText(msaT.getFullname());
        }

        @Override
        public int getItemCount(){
            return msaList.size();
        }

        @Override
        public Filter getFilter(){
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        msaListFiltered = msaList;
                    } else{
                        List<msaT> filteredList = new ArrayList<>();
                        for (msaT row : msaList){
                            //checking for name or staffID match
                            if(row.getStaff_id().toLowerCase().contains(charString.toLowerCase()) || row.getFullname().contains((charString.toLowerCase()))){
                                filteredList.add(row);
                            }
                        }
                        msaListFiltered = filteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = msaListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    msaListFiltered = (ArrayList<msaT>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

}


