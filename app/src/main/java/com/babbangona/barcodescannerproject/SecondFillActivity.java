package com.babbangona.barcodescannerproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SecondFillActivity extends AppCompatActivity implements View.OnClickListener{
    private DatePickerDialog dateTextDialog;
    private SimpleDateFormat dateFormatter;
    EditText dateText, confirmFillHSFId, confirmFillFieldID, confirmFillBags, mold_count, percentClean, percentMoisture, kg_marketed;
    Spinner confirmFillSeed;
    myDbAdapter helper;
    String confirmFillHsfString, confirmFillFieldIDString, confirmFIllBagsMarketedString,confirmFillSeedString, confirmFillDateString, loginName,
    confirmMoldCount, confirmPercentClean, confirmPercentMoisture, confirmKgMarketed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_fill);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        Intent openConfirmFillPage = getIntent();
        confirmFillHsfString = openConfirmFillPage.getStringExtra("Confirm_Fill_HSF_ID");
        confirmFillFieldIDString = openConfirmFillPage.getStringExtra("Confirm_Fill_Field_ID");
        confirmFIllBagsMarketedString = openConfirmFillPage.getStringExtra("Confirm_Fill_Bags_Marketed");
        confirmFillSeedString = openConfirmFillPage.getStringExtra("Confirm_Fill_Seed_Selected");
        confirmFillDateString = openConfirmFillPage.getStringExtra("Confirm_Fill_Date_Selected");
        // New columns
        confirmMoldCount = openConfirmFillPage.getStringExtra("Mold_Count");
        confirmPercentClean = openConfirmFillPage.getStringExtra("Percent_Clean");
        confirmPercentMoisture = openConfirmFillPage.getStringExtra("Percent_Moisture");
        confirmKgMarketed = openConfirmFillPage.getStringExtra("kg_marketed");
        // Stop New Columns

        confirmFillHSFId = findViewById(R.id.confirmFillHsfIdText);
        confirmFillFieldID = findViewById(R.id.confirmFillFieldIDText);
        confirmFillBags = findViewById(R.id.confirmFillBagsMarketedText);

        confirmFillSeed = findViewById(R.id.confirmFillSeedType);


        // New columns
        mold_count = findViewById(R.id.editMold_Count);
        percentClean = findViewById(R.id.editPercentClean);
        percentMoisture = findViewById(R.id.editPercentMoisture);
        kg_marketed = findViewById(R.id.editKgMarketed);

        List<String> list  = Master.getSeedType();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        confirmFillSeed.setAdapter(dataAdapter);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        findViewsById();
        setDateTimeField();
        helper = new myDbAdapter(this);

    }

    public void saveFillResults (View view) {

        String v1 = confirmFillHSFId.getText().toString();
        String v2 = confirmFillFieldID.getText().toString();
        String v3 = confirmFillBags.getText().toString();
        String v4 = confirmFillSeed.getSelectedItem().toString();
        String v5 = dateText.getText().toString();

        // New columns
        String mold = mold_count.getText().toString();
        String clean = percentClean.getText().toString();
        String moisture = percentMoisture.getText().toString();
        String kgMarketed = kg_marketed.getText().toString();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loginName = preferences.getString("Name", "");

        int fillBags = 0;

        try {
            fillBags = Integer.parseInt(v3);
        }
        catch(NumberFormatException nfe) {

        }

        int fieldLength = v2.length();

        if (v4.equals("Select One:")){
            Message.message(getApplicationContext(), "Please select a Seed Type" );
        }

        else if (mold.equals("")){
            Message.message(getApplicationContext(), "Please enter mold count" );
        }
        else if (clean.equals("")){
            Message.message(getApplicationContext(), "Please enter percent clean value" );
        }
        else if (moisture.equals("")){
            Message.message(getApplicationContext(), "Please enter percent moisture value" );
        }

        else {
            if (TextUtils.isEmpty(v1) || TextUtils.isEmpty(v2)
                    || TextUtils.isEmpty(v3) || TextUtils.isEmpty(v4) || TextUtils.isEmpty(v5)) {
                Message.message(getApplicationContext(), "One or more required fields are empty");
            } else {
                if (fieldLength != 18) {
                    Message.message(getApplicationContext(), "Wrong Field ID. Please ensure Field ID is entered correctly");
                }else {
                    if (confirmFillHsfString.equalsIgnoreCase(v1) && confirmFillFieldIDString.equalsIgnoreCase(v2) && confirmFIllBagsMarketedString.equalsIgnoreCase(v3)
                            && confirmFillSeedString.equalsIgnoreCase(v4) && confirmFillDateString.equalsIgnoreCase(v5)
                            && confirmMoldCount.equalsIgnoreCase(mold) && confirmPercentClean.equalsIgnoreCase(clean) && confirmPercentMoisture.equalsIgnoreCase(moisture) && confirmKgMarketed.equalsIgnoreCase(kgMarketed)
                            ) {
                        long id = helper.insertData(v1, v2, fillBags, v4, v5, loginName, mold, clean, moisture, kgMarketed);

                        if (id < 0) {
                            Message.message(getApplicationContext(), "Insertion Unsuccessful");
                        } else {
                            Message.message(getApplicationContext(), "Inventory Successfully Saved");
                            Intent openMainActivity = new Intent(SecondFillActivity.this, MainActivity.class);
                            startActivity(openMainActivity);

                            Intent finishSecondScanActivity = new Intent("finishFirstFill");
                            sendBroadcast(finishSecondScanActivity);

                            finish();

                        }
                    } else {
                        Message.message(getApplicationContext(), "Data entered doesn't correspond. Please go back to previous screen and re-enter data.");
                    }

                }
            }
        }


    }

    private void findViewsById(){
        dateText = findViewById(R.id.confirmFillDateText);
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
}
