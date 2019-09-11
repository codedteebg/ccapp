package com.babbangona.barcodescannerproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ShowInventory extends AppCompatActivity {
    myDbAdapter helper;
    private RecyclerView recycleView;
    Cursor cursor;
    String dateForList;
    BroadcastReceiver invShow_reciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent openShowInventory = getIntent();
        dateForList = openShowInventory.getStringExtra("Date_For_Inventory");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_inventory);

        invShow_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finishShowInventory")) {
                    //finishing the activity
                    finish();
                }
            }
        };
        registerReceiver(invShow_reciever, new IntentFilter("finishShowInventory"));

        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        helper = new myDbAdapter(this);

        SQLiteDatabase database = new myDbAdapter.myDbHelper(this).getReadableDatabase();

        String TABLE_NAME = "inventoryT";
        String UID = "_id";
        String HSFID = "HsfID";
        String FieldID = "FieldID";
        String Bags = "BagsMarketed";
        String Seed = "SeedType";
        String Date = "Date";
        String Name = "CCOName";
        String[] columns = {UID, HSFID, FieldID, Bags, Seed, Date, Name};

        cursor = database.query(TABLE_NAME, columns, Date + " =? AND deleted !=1", new String[] {dateForList}, null, null, null);


        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date dates = new Date();
        String date1 = format.format(dates);

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(date1));
            long date2 = calendar.getTimeInMillis();
        }catch (Exception e){}
        recycleView.setAdapter(new RecyclerViewCursorAdapter(this, cursor));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cursor.close();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                cursor.close();
                finish();
                break;
        }
        return true;
    }

    public void onDestroy() {
        unregisterReceiver(invShow_reciever);
        super.onDestroy();
    }
}