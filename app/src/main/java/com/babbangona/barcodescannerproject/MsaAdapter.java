package com.babbangona.barcodescannerproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.babbangona.barcodescannerproject.model.msaT;

import org.w3c.dom.Text;

import java.util.List;

public class MsaAdapter extends RecyclerView.Adapter<MsaAdapter.MyViewHolder> {
        private List<msaT> msaList;

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
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.msa_list_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position){
            msaT msaT = msaList.get(position);
            holder.msaStaff.setText(msaT.getStaff_id());
            holder.msaName.setText(msaT.getFullname());
        }

        @Override
        public int getItemCount(){
            return msaList.size();
        }

}


