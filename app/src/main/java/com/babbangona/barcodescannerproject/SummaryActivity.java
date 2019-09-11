package com.babbangona.barcodescannerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


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

    public void openMoldSummary(View view){
        Intent j = new Intent(this, SelectMoldSummary.class);
        startActivity(j);
    }

    public void openPercentCleanSummary(View view){
        Intent j = new Intent(this, SelectPercentCleanSummary.class);
        startActivity(j);
    }

    public void openPercentMoistureSummary(View view){
        Intent j = new Intent(this, SelectPercentMoistureSummary.class);
        startActivity(j);
    }

    public void openTotalSummary(View view){
        Intent k = new Intent(this, ShowTotalSummary.class);
        startActivity(k);
    }
}
