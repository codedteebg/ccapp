package com.babbangona.barcodescannerproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.database.AppExecutors;
import com.babbangona.barcodescannerproject.model.dateSummary;

public class ShowDateSummary extends AppCompatActivity {
    myDbAdapter helper;
    String dateForSummary, dateForLabel;
    long dateForSumQ;
    int totalDateBagsNo, totalDateTransNo;
    Date date;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date_summary);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent openDateSummary = getIntent();
        dateForSummary = openDateSummary.getStringExtra("Date_For_Summary");

        final TextInputEditText showBagsDate, showTransDate;
        TextView dateTitleLabel;
        String dateTitleLabelValue, totalBagsDateLabelValue, totalTransDateLabelValue;

        showBagsDate = findViewById(R.id.totalBagsDate);
        showTransDate =  findViewById(R.id.totalTransDate);
        dateTitleLabel =  findViewById(R.id.dateTitleLabel);

        disableInput(showBagsDate);
        disableInput(showTransDate);

        try {
            SimpleDateFormat srcFormat = new SimpleDateFormat("dd/MM/yyyy");
            date = srcFormat.parse(dateForSummary);
            SimpleDateFormat destFormat = new SimpleDateFormat("EEE, d MMM yyyy");
            dateForLabel = destFormat.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        dateTitleLabelValue = "Summary for " + dateForLabel;
        totalBagsDateLabelValue = "Total Bags Processed on " + dateForSummary;
        totalTransDateLabelValue = "Total Transactions Processed on " + dateForSummary;

        dateTitleLabel.setText(dateTitleLabelValue);
/*        totalBagsDateLabel.setText(totalBagsDateLabelValue);
        totalTransDateLabel.setText(totalTransDateLabelValue);*/

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final dateSummary dateSummary = mDb.inventoryTDao().showDateSummary(dateForSummary);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(dateSummary.toString().equalsIgnoreCase("")){
                            totalDateBagsNo = 0;
                            totalDateTransNo = 0;
                        } else {
                            totalDateBagsNo = dateSummary.getTotalBagsDate();
                            totalDateTransNo = dateSummary.getTxnDate();
                        }
                        showBagsDate.setText(String.valueOf(totalDateBagsNo));
                        showTransDate.setText(String.valueOf(totalDateTransNo));

                    }
                });
            }
        });

        /*helper = new myDbAdapter(this);
        SQLiteDatabase database = new myDbAdapter.myDbHelper(this).getReadableDatabase();

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(dateForSummary));
            dateForSumQ = calendar.getTimeInMillis();
        }
        catch (Exception e) {}

        Cursor bagsDateCursor = database.rawQuery("SELECT SUM(BagsMarketed) AS TotalBags FROM inventoryT WHERE Date = ? AND deleted !=1", new String[] {String.valueOf(dateForSumQ)});
        Cursor transDateCursor = database.rawQuery("SELECT _id FROM inventoryT WHERE Date = ? AND deleted !=1", new String[] {String.valueOf(dateForSumQ)});

        if (bagsDateCursor.moveToFirst()) {

            totalDateBagsNo = bagsDateCursor.getInt(bagsDateCursor.getColumnIndex("TotalBags"));
        }

        totalDateTransNo = transDateCursor.getCount();


        showBagsDate.setText(String.valueOf(totalDateBagsNo));
        showTransDate.setText(String.valueOf(totalDateTransNo));

        bagsDateCursor.close();
        transDateCursor.close();*/
    }

    public void showDateSummary(View view){
        Intent openShowInventory =  new Intent(this, ShowInventory.class);
        openShowInventory.putExtra("Date_For_Inventory", dateForSummary);
        Log.d("TobiLog", dateForSummary);
        startActivity(openShowInventory);

        }

    void disableInput (EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
    }
}
