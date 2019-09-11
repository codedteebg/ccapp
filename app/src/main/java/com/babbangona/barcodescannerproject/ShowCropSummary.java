package com.babbangona.barcodescannerproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

public class ShowCropSummary extends AppCompatActivity {
    myDbAdapter helper;
    String totalCropBags, totalCropTrans;
    int totalCropBagsNo;
    int totalCropTransNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_crop_summary);

        EditText showBagsCrop, showTransCrop;
        showBagsCrop = (EditText) findViewById(R.id.totalBagsCrop);
        showTransCrop = (EditText) findViewById(R.id.totalTransCrop);


        Intent openShowCropSummary = getIntent();
        String selectedSeedSummary = openShowCropSummary.getStringExtra("Crop_To_Summarise");

        TextView cropTitleLabel = (TextView) findViewById(R.id.cropTitleLabel);

        String cropTitleLabelValue = "Summary for "+selectedSeedSummary;

        cropTitleLabel.setText(cropTitleLabelValue);

        disableInput(showBagsCrop);
        disableInput(showTransCrop);

        helper = new myDbAdapter(this);


        SQLiteDatabase database = new myDbAdapter.myDbHelper(this).getReadableDatabase();


        Cursor bagsCropCursor = database.rawQuery("SELECT SUM(BagsMarketed) AS TotalBags FROM inventoryT WHERE SeedType = ? AND deleted !=1", new String[] {selectedSeedSummary});
        if (bagsCropCursor.moveToFirst()) {

            totalCropBagsNo = bagsCropCursor.getInt(bagsCropCursor.getColumnIndex("TotalBags"));
        }

        Cursor transCropCursor = database.rawQuery("SELECT _id FROM inventoryT WHERE SeedType = ? AND deleted !=1", new String[] {selectedSeedSummary});

       // if (transCropCursor != null) {
            totalCropTransNo = transCropCursor.getCount();
       /* } else
        {
            totalCropTransNo = 0;
        }
*/

        totalCropBags = String.valueOf(totalCropBagsNo);
        totalCropTrans = String.valueOf(totalCropTransNo);
        showBagsCrop.setText(totalCropBags);
        showTransCrop.setText(totalCropTrans);
        bagsCropCursor.close();
        transCropCursor.close();

    }

    void disableInput (EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
    }
}
