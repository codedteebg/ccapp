package com.babbangona.barcodescannerproject;

import android.app.DatePickerDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import com.babbangona.barcodescannerproject.model.transporterRate;

import org.w3c.dom.Text;

public class FillActivity extends AppCompatActivity implements View.OnClickListener{
    private DatePickerDialog dateTextDialog;
    private SimpleDateFormat dateFormatter;
    private TextInputEditText fillHsfID, fillFieldID, fillBags, dateText, mold_count, percentClean, percentMoisture, kg_marketed, filltransporterID;
    //private Spinner fillSeed;
    private AutoCompleteTextView fillSeed, fillTransporterRate;
    private String getFilledHSFID, getFilledFieldID, getFillBags, getFilledSeed, getFilledDate, getMoldCount, getPercentClean, getPercentMoisture, getKgMarketed, getFillTransporterID,  getFillTransporterRate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);

        fillSeed = findViewById(R.id.spinnerTest);
        fillTransporterRate = findViewById(R.id.fillTransporterRateText);
        //fillSeed = (Spinner) findViewById(R.id.fillSeedType);
        List<String> list = Master.getSeedType();
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);

        //fillSeed.setAdapter(dataAdapter);
        fillSeed.setAdapter(adapter);

        List<String> rateList = transporterRate.getRate();
        ArrayAdapter<String> rateAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, rateList);
        fillTransporterRate.setAdapter(rateAdapter);


        dateText =  findViewById(R.id.fillDateText);
        fillHsfID =  findViewById(R.id.fillHsfIdText);
        fillFieldID = findViewById(R.id.fillFieldIDText);
        fillBags = findViewById(R.id.fillBagsMarketedText);

        // New columns
        mold_count = findViewById(R.id.moldCountText);
        percentClean = findViewById(R.id.percentCleanText);
        percentMoisture = findViewById(R.id.percentMoistureText);
        kg_marketed = findViewById(R.id.kgMarketedText);
        filltransporterID = findViewById(R.id.fillTransporterText);

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
                //getFilledSeed = fillSeed.getSelectedItem().toString();
                getFilledSeed = fillSeed.getText().toString();
                getFilledDate = dateText.getText().toString();
                int fieldIdLenght = fillFieldID.getText().length();


                // New Columns
                getMoldCount = mold_count.getText().toString();
                getPercentClean = percentClean.getText().toString();
                getPercentMoisture = percentMoisture.getText().toString();
                getKgMarketed = kg_marketed.getText().toString();
                getFillTransporterID = filltransporterID.getText().toString();
                getFillTransporterRate = fillTransporterRate.getText().toString();



                if (getFilledSeed.equals("Select One:")){
                    Message.message(getApplicationContext(), "Please select a Seed Type" );
                }

                else {

                    if (TextUtils.isEmpty(getFilledHSFID) || TextUtils.isEmpty(getFilledFieldID)
                            || TextUtils.isEmpty(getFillBags) || TextUtils.isEmpty(getFilledSeed) || TextUtils.isEmpty(getFilledDate)
                            || TextUtils.isEmpty(getMoldCount) || TextUtils.isEmpty(getPercentClean) || TextUtils.isEmpty(getPercentMoisture)
                            || TextUtils.isEmpty(getKgMarketed) || TextUtils.isEmpty(getFillTransporterID) || TextUtils.isEmpty(getFillTransporterRate)) {
                        Message.message(getApplicationContext(), "One or more required fields are empty");

                    } else {
                        if (fieldIdLenght != 18) {
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
                            openConfirmFillPage.putExtra("Confirm_Fill_Transport", getFillTransporterID);
                            openConfirmFillPage.putExtra("Confirm_Fill_TransportRate", getFillTransporterRate);

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
        dateText = findViewById(R.id.fillDateText);
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
