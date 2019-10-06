package com.babbangona.barcodescannerproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.preference.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
//import android.widget.FilterQueryProvider;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity{
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    public int i;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDBHelper = new DatabaseHelper(LoginActivity.this);

        Intent intent = new Intent("finishMainActivity");
        sendBroadcast(intent);


        try {
            mDBHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            mDBHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        mDb = new DatabaseHelper(this).getReadableDatabase();
        final String TABLE_NAME = "employeeT";
        String UID = "_id";
        String Name = "employeeName";
        String Pass = "password";
        final String[] checkcols = {UID, Name, Pass};
        final String wherecols = Name + " =? and " + Pass + " =?";
        String[] querycols = {UID, Name};
        String[] adapterCols = {Name};
        int[] adapterRowViews = new int[]{android.R.id.text1};

        Cursor cursor = mDb.query(TABLE_NAME, querycols, null, null, null, null, Name+" ASC");

       /* SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_spinner_item, cursor, adapterCols, adapterRowViews, 0);
        cursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner textSpinner = (Spinner) findViewById(R.id.nameSpinner);

        textSpinner.setAdapter(cursorAdapter);*/
        final AutoCompleteTextView autoText = (AutoCompleteTextView) findViewById(R.id.autoCom);

        final String [] mydata;
        ArrayList<String> array = new ArrayList<>();
        cursor.moveToFirst();
        mydata = new String[cursor.getCount()];
        i=0;
        do  {
            mydata[i] = cursor.getString(1);//insert new stations to the array list
            //Log.i("ArrayList",mydata[i]);
            i++;
        }while(cursor.moveToNext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, mydata);

        autoText.setAdapter(adapter);

        Button show = findViewById(R.id.showButton);
        TextView refreshUsername = findViewById(R.id.refreshUsername);
        final EditText password = findViewById(R.id.password);

        show.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){
                String selectedName = autoText.getText().toString();
                String ePassword = password.getText().toString();
                String[] values = {selectedName, ePassword};
                Cursor c = mDb.query(TABLE_NAME, checkcols, wherecols, values, null, null, null);
                if (c != null) {
                    if(c.getCount() > 0) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Name",selectedName);
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, Main2Activity.class);
                        startActivity(i);
                        mDBHelper.close();
                        finish();
                    } else
                    {
                        Toast.makeText(LoginActivity.this, "Wrong login or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        refreshUsername.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v){

                Snackbar.make(findViewById(android.R.id.content), "Refreshing username list...\nYou will be notified when the process completes", Snackbar.LENGTH_LONG).show();

                SyncData.refreshUsername fetch_data_mss = new SyncData.refreshUsername(getApplicationContext()){

                    @Override
                    protected void onPostExecute(String s){

                        if(s.trim().equalsIgnoreCase("done")){
                            //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            Snackbar.make(findViewById(android.R.id.content), "Success", Snackbar.LENGTH_LONG).show();
                            Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "Failed", Snackbar.LENGTH_LONG).show();
                        }
                    }
                };

                fetch_data_mss.execute();
            }
        });

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