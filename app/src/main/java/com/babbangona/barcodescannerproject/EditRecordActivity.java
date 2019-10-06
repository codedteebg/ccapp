package com.babbangona.barcodescannerproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.database.AppExecutors;
import com.babbangona.barcodescannerproject.model.inventoryT;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EditRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText dateText, editHsfID, editFieldID, editBags, mold_count, percentClean, percentMoisture, kg_marketed, editTransporter;
    private AutoCompleteTextView editSeed;
    private DatePickerDialog dateTextDialog;
    private SimpleDateFormat dateFormatter;
    String hsfString;
    AppDatabase mDb;
    inventoryT inventoryT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Intent intent = new Intent("finishShowInventory");
        sendBroadcast(intent);

        Intent openEditRecords = getIntent();
        hsfString = openEditRecords.getStringExtra("ID_To_Edit");

        mDb = AppDatabase.getInstance(getApplicationContext());


        editHsfID =  findViewById(R.id.edHSFText);
        editFieldID = findViewById(R.id.edFieldIDText);
        editBags =  findViewById(R.id.edBagsText);
        dateText =   findViewById(R.id.editDateText);

        editSeed = findViewById(R.id.edSeedText);

        // New columns
        mold_count = findViewById(R.id.editMoldText);
        percentClean = findViewById(R.id.editCleanText);
        percentMoisture = findViewById(R.id.editMoistureText);
        kg_marketed = findViewById(R.id.edKGText);
        editTransporter = findViewById(R.id.editTransportText);

        List<String> list  = Master.getSeedType();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSeed.setAdapter(dataAdapter);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                inventoryT = mDb.inventoryTDao().retrieveHSF(hsfString);
                if(inventoryT != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editHsfID.setText(inventoryT.getHSFID());
                            editFieldID.setText(inventoryT.getFieldID());
                            editBags.setText(String.valueOf(inventoryT.getBagsMarketed()));
                            dateText.setText(inventoryT.getDateProcessed());
                            editSeed.setText(inventoryT.getSeedType());
                            mold_count.setText(String.valueOf(inventoryT.getMoldCount()));
                            percentClean.setText(String.valueOf(inventoryT.getPercentClean()));
                            percentMoisture.setText(String.valueOf(inventoryT.getPercentMoisture()));
                            kg_marketed.setText(String.valueOf(inventoryT.getKGMarketed()));
                            editTransporter.setText(inventoryT.getTransporterID());
                        }
                    });
                }
            }
        });


        findViewsById();
        setDateTimeField();

    }

    public void saveEditedRecord (View view){
        String u1, u2, u3, u4, u5, mold, clean, moisture, kgMarketed, transporter;
        int uBags = 0;

        u1 = editHsfID.getText().toString();
        u2 = editFieldID.getText().toString();
        u3 = editBags.getText().toString();

        // New columns
        mold = mold_count.getText().toString();
        clean = percentClean.getText().toString();
        moisture = percentMoisture.getText().toString();
        kgMarketed = kg_marketed.getText().toString();
        transporter = editTransporter.getText().toString();

        try {
            uBags = Integer.parseInt(u3);
        }
        catch(NumberFormatException nfe) {}

        u4 = editSeed.getText().toString();
        u5 = dateText.getText().toString();

        if (u4.equals("Select One:")){
            Message.message(getApplicationContext(), "Please select a Seed Type");
        }
        else {
            if (u1.isEmpty() || u2.isEmpty() || u3.isEmpty()
                    || u4.isEmpty() || u5.isEmpty() || kgMarketed.isEmpty()) {
                Message.message(getApplicationContext(), "One or more required fields are empty");
            } else {
            if (u2.length() != 18) {
                Message.message(getApplicationContext(), "Wrong Field ID. Please ensure Field ID is entered correctly");
            } else {
                inventoryT.setHSFID(u1);
                inventoryT.setFieldID(u2);
                inventoryT.setBagsMarketed(uBags);
                inventoryT.setKGMarketed(Integer.parseInt(kgMarketed));
                inventoryT.setDateProcessed(u5);
                inventoryT.setSeedType(u4);
                inventoryT.setMoldCount(Integer.parseInt(mold));
                inventoryT.setPercentClean(Integer.parseInt(clean));
                inventoryT.setPercentMoisture(Integer.parseInt(moisture));
                inventoryT.setTransporterID(transporter);
                inventoryT.setUpdateFlag(1);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final int iid = mDb.inventoryTDao().updateTxn(inventoryT);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(iid > 0){
                                    Message.message(getApplicationContext(), "Inventory Successfully Edited");
                                    Intent openMainAfterEdit = new Intent(EditRecordActivity.this, Main2Activity.class);
                                    startActivity(openMainAfterEdit );
                                    finish();
                                }
                                else {
                                    Message.message(getApplicationContext(), "Editing Unsuccessful");
                                }
                            }
                        });
                    }
                });
            }
        }

        }




    }

    private int getIndex(Spinner spinner, String myString) {


        int index = 0;

        for (int i=0;i< spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void findViewsById(){
        dateText = findViewById(R.id.editDateText);
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
