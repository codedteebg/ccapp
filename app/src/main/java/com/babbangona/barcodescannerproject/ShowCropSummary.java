package com.babbangona.barcodescannerproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;
import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.database.AppExecutors;

import com.babbangona.barcodescannerproject.model.cropSummary;
import com.google.android.material.textfield.TextInputEditText;

public class ShowCropSummary extends AppCompatActivity {
    myDbAdapter helper;
    String totalCropBags, totalCropTrans;
    int totalCropBagsNo;
    int totalCropTransNo;
    TextInputEditText showBagsCrop, showTransCrop;
    AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_crop_summary);

        mDb = AppDatabase.getInstance(getApplicationContext());


        showBagsCrop =  findViewById(R.id.totalBagsCrop);
        showTransCrop =  findViewById(R.id.totalTransCrop);


        Intent openShowCropSummary = getIntent();
        final String selectedSeedSummary = openShowCropSummary.getStringExtra("Crop_To_Summarise");

        TextView cropTitleLabel =  findViewById(R.id.cropTitleLabel);

        String cropTitleLabelValue = "Summary for "+selectedSeedSummary;

        cropTitleLabel.setText(cropTitleLabelValue);

        disableInput(showBagsCrop);
        disableInput(showTransCrop);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final cropSummary cropSummary = mDb.inventoryTDao().showCropSummary(selectedSeedSummary);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(cropSummary.toString().equalsIgnoreCase("")){
                            totalCropBagsNo  = 0;
                            totalCropTransNo = 0;
                        }
                        else {
                            totalCropBagsNo = cropSummary.getTotalBagsCrop();
                            totalCropTransNo = cropSummary.getTxnCrop();

                        totalCropBags = String.valueOf(totalCropBagsNo);
                        totalCropTrans = String.valueOf(totalCropTransNo);
                        showBagsCrop.setText(totalCropBags);
                        showTransCrop.setText(totalCropTrans);
                        }

                    }
                });
            }
        });

       /* helper = new myDbAdapter(this);


        SQLiteDatabase database = new myDbAdapter.myDbHelper(this).getReadableDatabase();


        Cursor bagsCropCursor = database.rawQuery("SELECT SUM(BagsMarketed) AS TotalBags FROM inventoryT WHERE SeedType = ? AND deleted !=1", new String[] {selectedSeedSummary});
        if (bagsCropCursor.moveToFirst()) {

            totalCropBagsNo = bagsCropCursor.getInt(bagsCropCursor.getColumnIndex("TotalBags"));
        }

        Cursor transCropCursor = database.rawQuery("SELECT _id FROM inventoryT WHERE SeedType = ? AND deleted !=1", new String[] {selectedSeedSummary});

       // if (transCropCursor != null) {
            totalCropTransNo = transCropCursor.getCount();*/
       /* } else
        {
            totalCropTransNo = 0;
        }
*/


        /*bagsCropCursor.close();
        transCropCursor.close();*/

    }

    void disableInput (EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
    }
}
