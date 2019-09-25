package com.babbangona.barcodescannerproject;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class SelectCropSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crop_summary);

        final AutoCompleteTextView cropSpinner;

        cropSpinner = findViewById(R.id.selectSeedType);
        List<String> list = Master.getSeedType();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,list);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSpinner.setAdapter(dataAdapter);

        Button nextCropSummary = findViewById(R.id.nextCropSummary);

        nextCropSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String GetSelectedSeed = cropSpinner.getText().toString();

                if (GetSelectedSeed.equals("Select One:")){
                    Toast.makeText(SelectCropSummary.this, "Please select a Seed Type",
                            Toast.LENGTH_SHORT).show();
                }

                else {
                    Intent openShowCropSummary = new Intent(SelectCropSummary.this, ShowCropSummary.class);
                    openShowCropSummary.putExtra("Crop_To_Summarise", GetSelectedSeed);
                    startActivity(openShowCropSummary);
                }

            }
        });
    }
}
