package com.babbangona.barcodescannerproject;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FillActivity extends AppCompatActivity implements View.OnClickListener{
    private DatePickerDialog dateTextDialog;
    private SimpleDateFormat dateFormatter;
    private EditText dateText, fillHsfID, fillFieldID, fillBags, mold_count, percentClean, percentMoisture, kg_marketed;
    private Spinner fillSeed;
    private String getFilledHSFID, getFilledFieldID, getFillBags, getFilledSeed, getFilledDate, getMoldCount, getPercentClean, getPercentMoisture, getKgMarketed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);

        fillSeed = (Spinner) findViewById(R.id.fillSeedType);
        List<String> list = Master.getSeedType();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fillSeed.setAdapter(dataAdapter);


        dateText = (EditText) findViewById(R.id.fillDateText);
        fillHsfID = (EditText) findViewById(R.id.fillHsfIdText);
        fillFieldID = (EditText) findViewById(R.id.fillFieldIDText);
        fillBags = (EditText) findViewById(R.id.fillBagsMarketedText);

        // New columns
        mold_count = findViewById(R.id.editMold_Count);
        percentClean = findViewById(R.id.editPercentClean);
        percentMoisture = findViewById(R.id.editPercentMoisture);
        kg_marketed = findViewById(R.id.editKgMarketed);

        /*
        BroadcastReceiver fill_receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finishFirstFill")) {
                    //finishing the activity
                    finish();
                }
            }
        };
        registerReceiver(fill_receiver, new IntentFilter("finishFirstFill"));
*/

        Button nextConfirmFill = (Button) findViewById(R.id.nextConfirmFill);

        nextConfirmFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFilledHSFID = fillHsfID.getText().toString();
                getFilledFieldID = fillFieldID.getText().toString();
                getFillBags = fillBags.getText().toString();
                getFilledSeed = fillSeed.getSelectedItem().toString();
                getFilledDate = dateText.getText().toString();
                int fieldIdLenght = fillFieldID.getText().length();


                // New Columns
                getMoldCount = mold_count.getText().toString();
                getPercentClean = percentClean.getText().toString();
                getPercentMoisture = percentMoisture.getText().toString();
                getKgMarketed = kg_marketed.getText().toString();

                if (getFilledSeed.equals("Select One:")){
                    Message.message(getApplicationContext(), "Please select a Seed Type" );
                }

                else {

                    if (TextUtils.isEmpty(getFilledHSFID) || TextUtils.isEmpty(getFilledFieldID)
                            || TextUtils.isEmpty(getFillBags) || TextUtils.isEmpty(getFilledSeed) || TextUtils.isEmpty(getFilledDate)) {
                        Message.message(getApplicationContext(), "One or more required fields are empty");

                    } else {
                        if (fieldIdLenght < 18 || fieldIdLenght >18) {
                            Message.message(getApplicationContext(), "Wrong Field ID. Please ensure Field ID is entered correctly");
                        }else {
                            Intent openConfirmFillPage = new Intent(FillActivity.this, SecondFillActivity.class);
                            openConfirmFillPage.putExtra("Confirm_Fill_HSF_ID", getFilledHSFID);
                            openConfirmFillPage.putExtra("Confirm_Fill_Field_ID", getFilledFieldID);
                            openConfirmFillPage.putExtra("Confirm_Fill_Bags_Marketed", getFillBags);
                            openConfirmFillPage.putExtra("Confirm_Fill_Seed_Selected", getFilledSeed);
                            openConfirmFillPage.putExtra("Confirm_Fill_Date_Selected", getFilledDate);

                            // New columns
                            openConfirmFillPage.putExtra("Mold_Count", getMoldCount);
                            openConfirmFillPage.putExtra("Percent_Clean", getPercentClean);
                            openConfirmFillPage.putExtra("Percent_Moisture", getPercentMoisture);
                            openConfirmFillPage.putExtra("kg_marketed", getKgMarketed);

                            startActivity(openConfirmFillPage);
                        }
                    }
                }

            }
        });

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        findViewsById();
        setDateTimeField();
    }


    private void findViewsById(){
        dateText = (EditText) findViewById(R.id.fillDateText);
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
