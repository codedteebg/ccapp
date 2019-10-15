package com.babbangona.barcodescannerproject;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;


public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
    }

    public void openCropSummary(View view){
        Intent i = new Intent(this, SelectCropSummary.class);
        startActivity(i);
    }

    public void openDateSummary(View view){
        Intent j = new Intent(this, SelectDateSummary.class);
        startActivity(j);
    }

    public void openTotalSummary(View view){
        Intent k = new Intent(this, ShowTotalSummary.class);
        startActivity(k);
    }

    public void openHSFTransport(View view){
        Intent l = new Intent(this, SelectPaidTransport.class);
        startActivity(l);
    }
}
