package com.babbangona.barcodescannerproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.babbangona.barcodescannerproject.model.hsfTransportT;

import java.util.ArrayList;
import java.util.List;

public class SelectHSFForTransport extends AppCompatActivity {
    private List<hsfTransportT> hsfTransportTList = new ArrayList<>();
    private RecyclerView hsfRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hsffor_transport);

        Toolbar toolbar = findViewById(R.id.toolbarHSF);
        setSupportActionBar(toolbar);




    }
}
