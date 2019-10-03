package com.babbangona.barcodescannerproject;

import android.view.View;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.babbangona.barcodescannerproject.model.hsfTransportT;

import java.util.List;

public class HSFTransportAdapter extends RecyclerView.Adapter<HSFTransportAdapter.MyViewHolder> implements Filterable {
    private List<hsfTransportT>  hsfTransportTList;
    private List<hsfTransportT>  hsfTransportFilteredList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView hsfID, fieldID, bagsMarketed, bagsRate, transporterID, amountToPay;

        public MyViewHolder(View view){
            super(view);

           hsfID = view.findViewById(R.id.hsfText);
           fieldID = view.findViewById(R.id.fieldText);
           bagsMarketed = view.findViewById(R.id.bagsText);
           transporterID = view.findViewById(R.id.transporterText);
           amountToPay = view.findViewById(R.id.amountText)
        }

        public HSFTransportAdapter(List<hsfTransportT> hsfTransportTList){

        }


    }

}
