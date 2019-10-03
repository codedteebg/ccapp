package com.babbangona.barcodescannerproject;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import com.babbangona.barcodescannerproject.database.AppExecutors;
import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.model.inventoryT;
import com.babbangona.barcodescannerproject.model.transporterRate;



public class ThirdScanActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText dateText, mold_count, percentClean, percentMoisture, kg_marketed, transporterId;
    private DatePickerDialog dateTextDialog;
    private SimpleDateFormat dateFormatter;
    private AutoCompleteTextView confirmTransportRateSpinner;
    private AppDatabase mDb;
    String confirmHsfString, confirmFieldIDString, confirmBagsMarketedString,confirmSeed, confirmDate, loginName,
            confirmMoldCount, confirmPercentClean, confirmPercentMoisture, confirmKgMarketed, confirmTransporterID, confirmTransporterRate;
    long iid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        TextInputEditText  confirmHSFID, confirmFieldID, confirmBagsMarketed;
        AutoCompleteTextView confirmSeedSpinner;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_scan);
        mDb = AppDatabase.getInstance(getApplicationContext());

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
        confirmTransporterID = openConfirmScanPage.getStringExtra("transporterID");
        confirmTransporterRate = openConfirmScanPage.getStringExtra("transporterRate");
        // Stop New Columns

        confirmHSFID =  findViewById(R.id.confirmHsfidText);
        confirmHSFID.setText(confirmHsfString);

        confirmFieldID = findViewById(R.id.confirmFieldIDText);
        confirmFieldID.setText(confirmFieldIDString);

        confirmBagsMarketed = findViewById(R.id.confirmBagsMarketedText);
        //confirmBagsMarketed.setText(confirmBagsMarketedString);

        confirmSeedSpinner = findViewById(R.id.confirmSeedTypeText);

        // New columns
        mold_count = findViewById(R.id.confirmScanMoldCountText);
        percentClean = findViewById(R.id.confirmScanCleanText);
        percentMoisture = findViewById(R.id.confirmScanMoistureText);
        kg_marketed = findViewById(R.id.scanKgMarketedText);
        transporterId = findViewById(R.id.confirmScanTransporterText);
        confirmTransportRateSpinner = findViewById(R.id.confirmScanTransporterRateText);

        List<String> list = Master.getSeedType();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, list);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        confirmSeedSpinner.setAdapter(dataAdapter);

        List<String> rateList = transporterRate.getRate();
        ArrayAdapter<String> rateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, rateList);
        confirmTransportRateSpinner.setAdapter(rateAdapter);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        findViewsById();
        setDateTimeField();

        //helper = new myDbAdapter(this);

    }

    public void saveScanResults (View view) {
        TextInputEditText saveScanHSF, saveScanField, saveScanBags, saveScanDate;
        AutoCompleteTextView saveScanSeed;
        int bags = 0;


        saveScanHSF =  findViewById(R.id.confirmHsfidText);
        saveScanField =  findViewById(R.id.confirmFieldIDText);
        saveScanBags =  findViewById(R.id.confirmBagsMarketedText);
        saveScanDate =  findViewById(R.id.confirmDateText);
        saveScanSeed =  findViewById(R.id.confirmSeedTypeText);

        // New columns
        mold_count = findViewById(R.id.confirmScanMoldCountText);
        percentClean = findViewById(R.id.confirmScanCleanText);
        percentMoisture = findViewById(R.id.confirmScanMoistureText);
        kg_marketed = findViewById(R.id.scanKgMarketedText);
        transporterId = findViewById(R.id.confirmScanTransporterText);
        // End New Columns

        String v1 = saveScanHSF.getText().toString();
        String v2 = saveScanField.getText().toString();
        String v3 = saveScanBags.getText().toString();
        String v5 = saveScanDate.getText().toString();
        String v4 = saveScanSeed.getText().toString();

        String mold = mold_count.getText().toString();
        String clean = percentClean.getText().toString();
        String moisture = percentMoisture.getText().toString();
        String kgMarketed = kg_marketed.getText().toString();
        String transporter = transporterId.getText().toString();
        String transporterRate = confirmTransportRateSpinner.getText().toString();


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
            if (v1.isEmpty() || v2.isEmpty() || v3.isEmpty() || v4.isEmpty() || v5.isEmpty() || transporter.isEmpty() || transporterRate.isEmpty()) {
                Message.message(getApplicationContext(), "One or more fields are empty");
            } else {
                if (confirmHsfString.equalsIgnoreCase(v1) && confirmFieldIDString.equalsIgnoreCase(v2) && confirmBagsMarketedString.equalsIgnoreCase(v3)
                        && confirmSeed.equalsIgnoreCase(v4) && confirmDate.equalsIgnoreCase(v5)
                        && confirmMoldCount.equalsIgnoreCase(mold) && confirmPercentClean.equalsIgnoreCase(clean) && confirmPercentMoisture.equalsIgnoreCase(moisture) && confirmKgMarketed.equalsIgnoreCase(kgMarketed)
                        && confirmTransporterID.equalsIgnoreCase(transporter) && confirmTransporterRate.equalsIgnoreCase(transporterRate)
                        ) {
                    final inventoryT inv = new inventoryT(v1, v2, bags, Integer.parseInt(kgMarketed), v4, v5, Integer.parseInt(mold),
                            Integer.parseInt(clean), Integer.parseInt(moisture), "NNNNNN", "CCOOOO", transporter,transporterRate);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            iid = mDb.inventoryTDao().insertTxn(inv);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (iid < 0) {
                                        Message.message(getApplicationContext(), "Insertion Unsuccessful");
                                    } else {
                                        Message.message(getApplicationContext(), "Inventory Successfully Saved");
                                        Intent openMainActivity = new Intent(ThirdScanActivity.this, MainActivity.class);
                                        startActivity(openMainActivity);

                                        Intent finishSecondScanActivity = new Intent("finishFirstFill");
                                        sendBroadcast(finishSecondScanActivity);

                                        finish();

                                    }
                                }
                            });

                        }
                    });
                /*
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



                    }*/
                } else {
                    //Message.message(getApplicationContext(), confirmDate);
                    Message.message(getApplicationContext(), "Data entered doesn't correspond. Please go back to previous screen and re-enter data.");
                }
            }
        }


    }

    private void findViewsById(){
        dateText = findViewById(R.id.confirmDateText);
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
