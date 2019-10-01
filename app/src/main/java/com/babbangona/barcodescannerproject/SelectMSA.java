package com.babbangona.barcodescannerproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.babbangona.barcodescannerproject.model.msaT;

import java.util.ArrayList;
import java.util.List;

public class SelectMSA extends AppCompatActivity {
    private List<msaT> msaTList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MsaAdapter msaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_msa);

        recyclerView = findViewById(R.id.msaRecyclerview);
        msaAdapter = new MsaAdapter(msaTList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(msaAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                msaT msaT = msaTList.get(position);
                Message.message(getApplicationContext(), msaT.getStaff_id() + "is selected");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMsaData();
    }

    private void prepareMsaData(){
        msaT msa = new msaT("T-1000000000001", "Tobi Adesanya", "jmfdnvhjsndbkhdskbkdshb");
        msaTList.add(msa);

        msa = new msaT("T-10000000000002", "Tolu Ajayi", "jdshfbyjfhfuhsfuihfiubduh");
        msaTList.add(msa);

        msaAdapter.notifyDataSetChanged();
    }
}
