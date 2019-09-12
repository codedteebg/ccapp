package com.babbangona.barcodescannerproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnFocusChangeListener;
import android.widget.Spinner;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.text.TextUtils;
import android.widget.Toast;

import android.widget.ArrayAdapter;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.lang.String;



public class SecondScanActivity extends AppCompatActivity implements OnClickListener {

    private DatePickerDialog dateTextDialog;
    private SimpleDateFormat dateFormatter;
    static private boolean isFirstTimeGetFocused = true;
    String GetHsfidText, GetFieldidText, GetBagsMarketedText, GetDateText, GetSeedText, getMoldCount, getPercentClean, getPercentMoisture, getKgMarketed;
    private EditText dateText, mold_count, percentClean, percentMoisture, kg_marketed;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_scan);

        final Spinner seedSpinner;
        Button nextConfirmScan;

        Intent intent = getIntent();
        final String hsf_string = intent.getStringExtra("HSF_ID");
        final String field_string = intent.getStringExtra("FIELD_ID");
        String bags_string = intent.getStringExtra("BAGS_MKTD");
        String seed_string = intent.getStringExtra("SEED_TYPE");

        final EditText hsfidText = (EditText) findViewById(R.id.hsfidText);
        hsfidText.setText(hsf_string);

        final EditText fieldIDText = (EditText) findViewById(R.id.fieldIDText);
        fieldIDText.setText(field_string);

        final EditText bagsMarketedText = (EditText) findViewById(R.id.bagsMarketedText);
        bagsMarketedText.setText(bags_string);
        bagsMarketedText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus && isFirstTimeGetFocused){
                    bagsMarketedText.setText("");
                    isFirstTimeGetFocused = false;
                }
            }
        });


        // New columns
        mold_count = findViewById(R.id.editMold_Count);
        percentClean = findViewById(R.id.editPercentClean);
        percentMoisture = findViewById(R.id.editPercentMoisture);
        kg_marketed = findViewById(R.id.editKgMarketed);
        // End New Columns

        seedSpinner = (Spinner) findViewById(R.id.seedType);
        List<String> list = Master.getSeedType();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seedSpinner.setAdapter(dataAdapter);

        seedSpinner.setSelection(getIndex(seedSpinner, seed_string));
        disableInput(hsfidText);
        disableInput(fieldIDText);

        dateText = (EditText) findViewById(R.id.dateText);

        BroadcastReceiver broadcast_receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finishSecondScan")) {
                //finishing the activity
                    finish();
                }
            }
        };
        registerReceiver(broadcast_receiver, new IntentFilter("finishSecondScan"));


        nextConfirmScan = (Button) findViewById(R.id.nextConfirmScan);

        nextConfirmScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                GetHsfidText = hsfidText.getText().toString();
                GetFieldidText = fieldIDText.getText().toString();
                GetBagsMarketedText = bagsMarketedText.getText().toString();
                GetDateText = dateText.getText().toString();
                GetSeedText = seedSpinner.getSelectedItem().toString();

                // New Columns
                getMoldCount = mold_count.getText().toString();
                getPercentClean = percentClean.getText().toString();
                getPercentMoisture = percentMoisture.getText().toString();
                getKgMarketed = kg_marketed.getText().toString();

                if (GetSeedText.equals("Select One:")){
                    Toast.makeText(SecondScanActivity.this, "Please select a Seed Type",
                            Toast.LENGTH_SHORT).show();
                }

                else {

                    if (TextUtils.isEmpty(GetHsfidText) || TextUtils.isEmpty(GetFieldidText)
                            || TextUtils.isEmpty(GetBagsMarketedText) || TextUtils.isEmpty(GetSeedText) || TextUtils.isEmpty(GetDateText)) {
                        Toast.makeText(SecondScanActivity.this, "One or more required fields are empty",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        Intent openConfirmScanPage = new Intent(SecondScanActivity.this, ThirdScanActivity.class);
                        openConfirmScanPage.putExtra("Confirm_HSF_ID", GetHsfidText);
                        openConfirmScanPage.putExtra("Confirm_Field_ID", GetFieldidText);
                        openConfirmScanPage.putExtra("Confirm_Bags_Marketed", GetBagsMarketedText);
                        openConfirmScanPage.putExtra("Confirm_Seed_Selected", GetSeedText);
                        openConfirmScanPage.putExtra("Confirm_Date_Selected", GetDateText);

                        // New columns
                        openConfirmScanPage.putExtra("Mold_Count", getMoldCount);
                        openConfirmScanPage.putExtra("Percent_Clean", getPercentClean);
                        openConfirmScanPage.putExtra("Percent_Moisture", getPercentMoisture);
                        openConfirmScanPage.putExtra("kg_marketed", getKgMarketed);

                        startActivity(openConfirmScanPage);
                    }
                }
            }
        });

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        findViewsById();
        setDateTimeField();
    }

    private int getIndex(Spinner spinner, String myString)
    {

        int index = 0;

        for (int i=0;i< spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    void disableInput (EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
    }

    private void findViewsById(){
        dateText = (EditText) findViewById(R.id.dateText);
        dateText.setInputType(InputType.TYPE_NULL);
        dateText.requestFocus();
    }

    private void setDateTimeField() {
        dateText.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        dateTextDialog = new DatePickerDialog(this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateText.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH) );
    }

    @Override
    public void onClick(View view) {
            dateTextDialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent openScanMain = new Intent(SecondScanActivity.this, MainActivity.class);
        startActivity(openScanMain);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent j = new Intent(SecondScanActivity.this, MainActivity.class);
                startActivity(j);
                finish();
                break;
        }
        return true;
    }


}
