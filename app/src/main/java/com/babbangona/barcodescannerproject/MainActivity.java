package com.babbangona.barcodescannerproject;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;

import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.database.AppExecutors;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import com.babbangona.barcodescannerproject.model.inventoryT;


public class MainActivity extends AppCompatActivity {

    myDbAdapter helper;

    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ComponentName componentName;
    private static final int JOB_ID =101;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());
        // Sync triggered whenever you visit the home page
        SyncData.SyncInventory syncTest = new SyncData.SyncInventory(getApplicationContext()){
            @Override
            protected void onPostExecute(String s){
            }
        };
        syncTest.execute();
        // End code stub

        // Background Sync

        componentName = new ComponentName(this, BackgroundSync.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
            builder.setMinimumLatency(5000);

            //builder.setTriggerContentMaxDelay(100000);
            builder.setPersisted(true);
            jobInfo = builder.build();
            jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(jobInfo);
            // Toast.makeText(this, "Job Scheduled", Toast.LENGTH_SHORT).show();

        } else {

            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
            builder.setPeriodic(60000);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            builder.setPersisted(true);
            jobInfo = builder.build();
            jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(jobInfo);
        }
        requestPermissions();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 10);
        }
    }

    public void openScanScreen(View view) {
        Intent i = new Intent(this, DefaultActivity.class);
        startActivity(i);

    }

    private void requestPermissions(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }

    }

    public void openFillScreen(View view) {
        Intent j = new Intent(this, FillActivity.class);
        startActivity(j);
    }

    public void showSummary(View view) {
        Intent j = new Intent(this, SummaryActivity.class);
        startActivity(j);
    }

    public void exportInventory(View view) {

        helper = new myDbAdapter(this);

        Cursor exportCursor = helper.getData();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        String todaysDate = new SimpleDateFormat("ddMMyyyy").format(calendar.getTime());

        String csvFile = "inv_" + todaysDate + "_" + System.currentTimeMillis() + ".xls";

        String dir = Environment.getExternalStorageDirectory().getPath() + "/InventoryExport/";

        File directory = new File(dir);
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path
            File file = new File(directory, csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("inventory", 0);

            sheet.addCell(new Label(0, 0, "HSFID")); // column and row
            sheet.addCell(new Label(1, 0, "FieldID"));
            sheet.addCell(new Label(2, 0, "Bags"));
            sheet.addCell(new Label(3, 0, "Seed"));
            sheet.addCell(new Label(4, 0, "Date"));
            sheet.addCell(new Label(5, 0, "CCOName"));

            sheet.addCell(new Label(6, 0, "Mold_Count"));
            sheet.addCell(new Label(7, 0, "Percent_Clean"));
            sheet.addCell(new Label(8, 0, "Percent_Moisture"));
            sheet.addCell(new Label(9, 0, "KG_Marketed"));

            String hsfInput, fieldInput, bagsInput, seedInput, ccoInput, dateInput, mold_count, percentClean, percentMoisture, kg_marketed;
            Calendar cal;

            if (exportCursor.moveToFirst()) {
                do {
                    hsfInput = exportCursor.getString(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.HSFID));
                    fieldInput = exportCursor.getString(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.FieldID));
                    bagsInput = exportCursor.getString(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Bags));
                    seedInput = exportCursor.getString(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Seed));
                    cal = Calendar.getInstance();
                    cal.setTimeInMillis(exportCursor.getLong(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Date)));
                    dateInput = new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());

                    ccoInput = exportCursor.getString(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.CCOName));

                    mold_count = exportCursor.getString(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Mold_Count));
                    percentClean = exportCursor.getString(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Percent_Clean));
                    percentMoisture = exportCursor.getString(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Percent_Moisture));
                    kg_marketed = exportCursor.getString(exportCursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.KG_Marketed));

                    int i = exportCursor.getPosition() + 1;
                    sheet.addCell(new Label(0, i, hsfInput));
                    sheet.addCell(new Label(1, i, fieldInput));
                    sheet.addCell(new Label(2, i, bagsInput));
                    sheet.addCell(new Label(3, i, seedInput));
                    sheet.addCell(new Label(4, i, dateInput));
                    sheet.addCell(new Label(5, i, ccoInput));

                    // New columns
                    sheet.addCell(new Label(6, i, mold_count));
                    sheet.addCell(new Label(7, i, percentClean));
                    sheet.addCell(new Label(8, i, percentMoisture));
                    sheet.addCell(new Label(9, i, kg_marketed));
                } while (exportCursor.moveToNext());
            }
            //closing cursor
            exportCursor.close();
            workbook.write();
            workbook.close();
            Toast.makeText(getApplication(), "Data Exported in an Excel Sheet", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void syncInventory(View view) {

        Snackbar.make(findViewById(android.R.id.content), "Sending data...\nYou will be notified when the process completes", Snackbar.LENGTH_SHORT).show();

        SyncData.SyncInventory syncTest = new SyncData.SyncInventory(getApplicationContext()){

            @Override
            protected void onPostExecute(String s){
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
        };

        syncTest.execute();

    }

    //Used in an onClick method here. Just add to your onCreate, etc....
    public void csvImport(View view){
        final ArrayList<inventoryT> invs = readCSVFromAssets(); //gets List of objects from readCSV method

        /*
         * I am using Executors here. Your AsyncTask will also work.
        Will attach the Executors class also in case you need them.
         */
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long[] id = mDb.inventoryTDao().insertPreTxn(invs); //DAO call to insert objects
                //returns a long[] to ensure that it actually inserts.
                if (id.length > 0){
                    //because Toasts can't run on Background.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Message class handles toasts for me.
                            Message.message(getApplicationContext(), "Data Preloaded");
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Message.message(getApplicationContext(), "Error insertin data");
                        }
                    });
                }
            }
        });
    }

    //CSV read into List of model class
    private ArrayList<inventoryT> readCSVFromAssets(){
        ArrayList<inventoryT> inventoryTS = new ArrayList<>(); //List to hold model class objects
        String[] content = null; //placeholder String array
        try{
            //opens CSV fle in asset folder
            InputStream inputStream = getAssets().open("inventoryT.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            //read from CSV file by line and read into object. Also adds into the list.
            while ((line = br.readLine()) != null){
                content = line.split(",");
                //To escape out the header row
                if (content[0].equals("HSFID")){

                }else{
                 //maps CSV row to model class and adds to list
                final inventoryT inv = new inventoryT(
                     content[0],
                     content[1],
                     content[2],
                     Integer.parseInt(content[3]),
                     Integer.parseInt(content[4]),
                     content[5],
                     content[6],
                     Integer.parseInt(content[7]),
                     Integer.parseInt(content[8]),
                     Integer.parseInt(content[9]));
                inventoryTS.add(inv);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return inventoryTS; //return List
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent openLogin= new Intent(this, LoginActivity.class);
        startActivity(openLogin);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent openLogin= new Intent(this, LoginActivity.class);
                startActivity(openLogin);
                finish();
                break;
        }
        return true;
    }


}