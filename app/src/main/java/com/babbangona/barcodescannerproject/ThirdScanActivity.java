package com.babbangona.barcodescannerproject;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ThirdScanActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText dateText, mold_count, percentClean, percentMoisture, kg_marketed;
    private DatePickerDialog dateTextDialog;
    private SimpleDateFormat dateFormatter;
    myDbAdapter helper;
    String confirmHsfString, confirmFieldIDString, confirmBagsMarketedString,confirmSeed, confirmDate, loginName,
            confirmMoldCount, confirmPercentClean, confirmPercentMoisture, confirmKgMarketed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Spinner confirmSeedSpinner;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_scan);

        Intent openConfirmScanPage = getIntent();
        confirmHsfString = openConfirmScanPage.getStringExtra("Confirm_HSF_ID");
        confirmFieldIDString = openConfirmScanPage.getStringExtra("Confirm_Field_ID");
        confirmBagsMarketedString = openConfirmScanPage.getStringExtra("Confirm_Bags_Marketed");
        confirmSeed = openConfirmScanPage.getStringExtra("Confirm_Seed_Selected");
        confirmDate = openConfirmScanPage.getStringExtra("Confirm_Date_Selected");

        // New columns
        confirmMoldCount = openConfirmScanPage.getStringExtra("Mold_Count");
        confirmPercentClean = openConfirmScanPage.getStringExtra("Percent_Clean");
        confirmPercentMoisture = openConfirmScanPage.getStringExtra("Percent_Moisture");
        confirmKgMarketed = openConfirmScanPage.getStringExtra("kg_marketed");
        // Stop New Columns

        EditText confirmHSFID = (EditText) findViewById(R.id.confirmHsfidText);
        confirmHSFID.setText(confirmHsfString);

        EditText confirmFieldID = (EditText) findViewById(R.id.confirmFieldIDText);
        confirmFieldID.setText(confirmFieldIDString);

        EditText confirmBagsMarketed = (EditText) findViewById(R.id.confirmBagsMarketedText);
        confirmBagsMarketed.setText(confirmBagsMarketedString);

        confirmSeedSpinner = (Spinner) findViewById(R.id.confirmSeedType);

        // New columns
        mold_count = findViewById(R.id.editMold_Count);
        percentClean = findViewById(R.id.editPercentClean);
        percentMoisture = findViewById(R.id.editPercentMoisture);
        kg_marketed = findViewById(R.id.editKgMarketed);

        List<String> list = Master.getSeedType();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        confirmSeedSpinner.setAdapter(dataAdapter);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        findViewsById();
        setDateTimeField();

        helper = new myDbAdapter(this);

    }

    public void saveScanResults (View view) {
        EditText saveScanHSF, saveScanField, saveScanBags, saveScanDate;
        Spinner saveScanSeed;
        int bags = 0;


        saveScanHSF = (EditText) findViewById(R.id.confirmHsfidText);
        saveScanField = (EditText) findViewById(R.id.confirmFieldIDText);
        saveScanBags = (EditText) findViewById(R.id.confirmBagsMarketedText);
        saveScanDate = (EditText) findViewById(R.id.confirmDateText);
        saveScanSeed = (Spinner) findViewById(R.id.confirmSeedType);

        // New columns
        mold_count = findViewById(R.id.editMold_Count);
        percentClean = findViewById(R.id.editPercentClean);
        percentMoisture = findViewById(R.id.editPercentMoisture);
        kg_marketed = findViewById(R.id.editKgMarketed);
        // End New Columns

        String v1 = saveScanHSF.getText().toString();
        String v2 = saveScanField.getText().toString();
        String v3 = saveScanBags.getText().toString();
        String v5 = saveScanDate.getText().toString();
        String v4 = saveScanSeed.getSelectedItem().toString();

        String mold = mold_count.getText().toString();
        String clean = percentClean.getText().toString();
        String moisture = percentMoisture.getText().toString();
        String kgMarketed = kg_marketed.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loginName = preferences.getString("Name", "");

        try {
            bags = Integer.parseInt(v3);
        }
        catch(NumberFormatException nfe) {

        }

        if (v4.equals("Select One:")){
            Message.message(getApplicationContext(), "Please select a Seed Type");
        }else if (mold.equals("")){
            Message.message(getApplicationContext(), "Please enter mold count value");
        }else if (clean.equals("")){
            Message.message(getApplicationContext(), "Please enter percent clean value");
        }else if (moisture.equals("")){
            Message.message(getApplicationContext(), "Please enter percent moisture value");
        }else if (kgMarketed.equals("")){
            Message.message(getApplicationContext(), "Please enter kg marketed value");
        }
        else {
            if (v1.isEmpty() || v2.isEmpty() || v3.isEmpty() || v4.isEmpty() || v5.isEmpty()) {
                Message.message(getApplicationContext(), "One or more fields are empty");
            } else {
                if (confirmHsfString.equalsIgnoreCase(v1) && confirmFieldIDString.equalsIgnoreCase(v2) && confirmBagsMarketedString.equalsIgnoreCase(v3)
                        && confirmSeed.equalsIgnoreCase(v4) && confirmDate.equalsIgnoreCase(v5)
                        && confirmMoldCount.equalsIgnoreCase(mold) && confirmPercentClean.equalsIgnoreCase(clean) && confirmPercentMoisture.equalsIgnoreCase(moisture) && confirmKgMarketed.equalsIgnoreCase(kgMarketed)
                        ) {
                    long id = helper.insertData(v1, v2, bags, v4, v5, loginName, mold, clean, moisture, kgMarketed);
                    if (id < 0) {
                        Message.message(getApplicationContext(), "Insertion Unsuccessful");
                    } else {
                        Message.message(getApplicationContext(), "Inventory Successfully Saved");
                        Intent openMainActivity = new Intent(ThirdScanActivity.this, MainActivity.class);
                        startActivity(openMainActivity);

                        Intent finishSecondScanActivity = new Intent("finishSecondScan");
                        sendBroadcast(finishSecondScanActivity);

                        finish();



                    }
                } else {
                    //Message.message(getApplicationContext(), confirmDate);
                    Message.message(getApplicationContext(), "Data entered doesn't correspond. Please go back to previous screen and re-enter data.");
                }
            }
        }


    }

    private void findViewsById(){
        dateText = (EditText) findViewById(R.id.confirmDateText);
        dateText.setInputType(InputType.TYPE_NULL);
        dateText.requestFocus();
    }

    private void setDateTimeField() {
        dateText.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        dateTextDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
