package com.babbangona.barcodescannerproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.EditText;
import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.database.AppExecutors;
import com.babbangona.barcodescannerproject.model.totalSummary;

import com.google.android.material.textfield.TextInputEditText;

public class ShowTotalSummary extends AppCompatActivity {
    myDbAdapter helper;
    String totalBags, totalTrans;
    int totalBagsNo;
    int totalTransNo;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_total_summary);

        mDb = AppDatabase.getInstance(getApplicationContext());

        final TextInputEditText showBags, showTrans;
        showBags = findViewById(R.id.totalBags);
        showTrans = findViewById(R.id.totalTrans);



        disableInput(showBags);
        disableInput(showTrans);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final totalSummary totalSummary = mDb.inventoryTDao().showTotalSummary();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(totalSummary.toString().equalsIgnoreCase("")){
                            totalBagsNo = 0;
                            totalTransNo = 0;
                        } else {
                            totalBagsNo = totalSummary.getTotalBagsTotal();
                            totalTransNo = totalSummary.getTxnTotal();
                            totalBags = String.valueOf(totalBagsNo);
                            totalTrans = String.valueOf(totalTransNo);
                            showBags.setText(totalBags);
                            showTrans.setText(totalTrans);

                        }

                    }
                });
            }
        });


        /*helper = new myDbAdapter(this);


        SQLiteDatabase database = new myDbAdapter.myDbHelper(this).getReadableDatabase();


        Cursor bagsCursor = database.rawQuery("SELECT SUM(BagsMarketed) AS TotalBags FROM inventoryT WHERE deleted !=1", null);
        if (bagsCursor.moveToFirst()) {

            totalBagsNo = bagsCursor.getInt(bagsCursor.getColumnIndex("TotalBags"));
        }

        Cursor transCursor = database.rawQuery("SELECT _id FROM inventoryT WHERE deleted !=1", null);

        // if (transCropCursor != null) {
        totalTransNo = transCursor.getCount();
       *//* } else
        {
            totalCropTransNo = 0;
        }
*//*

        totalBags = String.valueOf(totalBagsNo);
        totalTrans = String.valueOf(totalTransNo);
        showBags.setText(totalBags);
        showTrans.setText(totalTrans);
        bagsCursor.close();
        transCursor.close();*/

    }

    void disableInput (TextInputEditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
