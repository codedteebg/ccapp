package com.babbangona.barcodescannerproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class ShowPercentMoistureSummary extends AppCompatActivity {

    myDbAdapter helper;
    HashMap<String, String> totalCropBagsNo;
    int totalCropTransNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_percent_moisture_summary);

        EditText showBagsCrop, showTransCrop;
        showBagsCrop = findViewById(R.id.totalBagsCrop);
        showTransCrop = findViewById(R.id.totalTransCrop);


        Intent openShowCropSummary = getIntent();
        String selectedSeedSummary = openShowCropSummary.getStringExtra("Crop_To_Summarise");

        TextView cropTitleLabel = findViewById(R.id.cropTitleLabel);

        String cropTitleLabelValue = "Summary for "+selectedSeedSummary;

        cropTitleLabel.setText(cropTitleLabelValue);

        disableInput(showBagsCrop);
        disableInput(showTransCrop);

        helper = new myDbAdapter(this);


        SQLiteDatabase database = new myDbAdapter.myDbHelper(this).getReadableDatabase();

        totalCropBagsNo = helper.getPercentMoisture(getApplicationContext(), 12.0, selectedSeedSummary);

        Cursor transCropCursor = database.rawQuery("SELECT _id FROM inventoryT WHERE SeedType = ? AND deleted !=1", new String[] {selectedSeedSummary});

        // if (transCropCursor != null) {
        totalCropTransNo = transCropCursor.getCount();
       /* } else
        {
            totalCropTransNo = 0;
        }
*/

        String high = totalCropBagsNo.get("high");
        String low = totalCropBagsNo.get("low");
        showBagsCrop.setText(high);
        showTransCrop.setText(low);

    }

    void disableInput (EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
    }

}
