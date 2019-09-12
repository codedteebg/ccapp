package com.babbangona.barcodescannerproject;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class SelectPercentCleanSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_percent_clean_summary);

        final Spinner cropSpinner;

        cropSpinner = findViewById(R.id.selectSeedType);
        List<String> list = Master.getSeedType();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSpinner.setAdapter(dataAdapter);

        Button nextCropSummary = (Button) findViewById(R.id.nextCropSummary);

        nextCropSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String GetSelectedSeed = cropSpinner.getSelectedItem().toString();

                if (GetSelectedSeed.equals("Select One:")){
                    Toast.makeText(getApplicationContext(), "Please select a Seed Type",
                            Toast.LENGTH_SHORT).show();
                }

                else {
                    Intent openShowCropSummary = new Intent(getApplicationContext(), ShowPercentCleanSummary.class);
                    openShowCropSummary.putExtra("Crop_To_Summarise", GetSelectedSeed);
                    startActivity(openShowCropSummary);
                }

            }
        });
    }
}
