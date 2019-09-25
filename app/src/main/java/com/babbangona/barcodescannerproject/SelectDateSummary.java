package com.babbangona.barcodescannerproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SelectDateSummary extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText dateText;
    private DatePickerDialog dateTextDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_summary);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        findViewsById();
        setDateTimeField();
    }

    public void showDateSummary (View view){
        TextInputEditText summaryDate =  findViewById(R.id.selectDate);

        String summDate = summaryDate.getText().toString();

        if(summDate.isEmpty()) {
            Message.message(getApplicationContext(), "Please select a date for summary");
        } else
        {
            Intent openDateSummary = new Intent(SelectDateSummary.this, ShowDateSummary.class);
            openDateSummary.putExtra("Date_For_Summary", summDate);
            startActivity(openDateSummary);
        }


    }

    private void findViewsById(){
        dateText =  findViewById(R.id.selectDate);
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
