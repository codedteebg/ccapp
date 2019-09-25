package com.babbangona.barcodescannerproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShowDateSummary extends AppCompatActivity {
    myDbAdapter helper;
    String dateForSummary, dateForLabel;
    long dateForSumQ;
    int totalDateBagsNo, totalDateTransNo;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date_summary);

        Intent openDateSummary = getIntent();
        dateForSummary = openDateSummary.getStringExtra("Date_For_Summary");

        TextInputEditText showBagsDate, showTransDate;
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

        helper = new myDbAdapter(this);
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
        transDateCursor.close();
    }

    public void showDateSummary(View view){
        Intent openShowInventory =  new Intent(this, ShowInventory.class);
        openShowInventory.putExtra("Date_For_Inventory", String.valueOf(dateForSumQ));
        startActivity(openShowInventory);

        }

    void disableInput (EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
    }
}
