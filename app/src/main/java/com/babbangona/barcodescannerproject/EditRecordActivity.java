package com.babbangona.barcodescannerproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EditRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText dateText, editHsfID, editFieldID, editBags, mold_count, percentClean, percentMoisture, kg_marketed;
    private Spinner editSeed;
    private DatePickerDialog dateTextDialog;
    private SimpleDateFormat dateFormatter;
    myDbAdapter helper;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Intent intent = new Intent("finishShowInventory");
        sendBroadcast(intent);

        Intent openEditRecords = getIntent();
        String uidString = openEditRecords.getStringExtra("ID_To_Edit");

        uid = Integer.parseInt(uidString);

        editHsfID = (EditText) findViewById(R.id.editHsfidText);
        editFieldID = (EditText) findViewById(R.id.editFieldIDText);
        editBags = (EditText) findViewById(R.id.editBagsMarketedText);
        dateText = (EditText) findViewById(R.id.editDateText);

        editSeed = (Spinner) findViewById(R.id.editSeedType);

        // New columns
        mold_count = findViewById(R.id.editMold_Count);
        percentClean = findViewById(R.id.editPercentClean);
        percentMoisture = findViewById(R.id.editPercentMoisture);
        kg_marketed = findViewById(R.id.editKgMarketed);

        List<String> list  = Master.getSeedType();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editSeed.setAdapter(dataAdapter);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        helper = new myDbAdapter(this);

        SQLiteDatabase database = new myDbAdapter.myDbHelper(this).getReadableDatabase();

        Cursor selectCursor = database.rawQuery("SELECT * FROM inventoryT WHERE _id = ?", new String[] {uidString});

        if (selectCursor != null) {
            selectCursor.moveToFirst();
            editHsfID.setText(selectCursor.getString(selectCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.HSFID)));
            editFieldID.setText(selectCursor.getString(selectCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.FieldID)));
            editBags.setText(selectCursor.getString(selectCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Bags)));

            mold_count.setText(selectCursor.getString(selectCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Mold_Count)));
            percentClean.setText(selectCursor.getString(selectCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Percent_Clean)));
            percentMoisture.setText(selectCursor.getString(selectCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Percent_Moisture)));
            kg_marketed.setText(selectCursor.getString(selectCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.KG_Marketed)));

            Long dateInMilli = selectCursor.getLong(selectCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Date));

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(new Date(dateInMilli));

            dateText.setText(dateString);
            String cursorSeed = selectCursor.getString(selectCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Seed));
            editSeed.setSelection(getIndex(editSeed, cursorSeed));
        }

        findViewsById();
        setDateTimeField();

    }

    public void saveEditedRecord (View view){
        String u1, u2, u3, u4, u5, mold, clean, moisture, kgMarketed;
        int uBags = 0;

        u1 = editHsfID.getText().toString();
        u2 = editFieldID.getText().toString();
        u3 = editBags.getText().toString();

        // New columns
        mold = mold_count.getText().toString();
        clean = percentClean.getText().toString();
        moisture = percentMoisture.getText().toString();
        moisture = percentMoisture.getText().toString();
        kgMarketed = kg_marketed.getText().toString();

        try {
            uBags = Integer.parseInt(u3);
        }
        catch(NumberFormatException nfe) {}

        u4 = editSeed.getSelectedItem().toString();
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
                long count = helper.updateRecord(uid, u1, u2, uBags, u4, u5, mold, clean, moisture, kgMarketed );
                if (count < 0) {
                    Message.message(getApplicationContext(), "Editing Unsuccessful");
                } else {
                    Message.message(getApplicationContext(), "Inventory Successfully Edited");
                    Intent openMainAfterEdit = new Intent(EditRecordActivity.this, MainActivity.class);
                    startActivity(openMainAfterEdit );
                    finish();

                }
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
        dateText = (EditText) findViewById(R.id.editDateText);
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
