package com.babbangona.barcodescannerproject;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Environment;

import com.babbangona.barcodescannerproject.api.ApiClient;
import com.babbangona.barcodescannerproject.api.ApiInterface;
import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.database.AppExecutors;
import com.babbangona.barcodescannerproject.model.hsfTransportT;
import com.babbangona.barcodescannerproject.model.msaResponseT;
import com.babbangona.barcodescannerproject.model.msaT;
import com.babbangona.barcodescannerproject.model.syncHSFResponse;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.babbangona.barcodescannerproject.model.inventoryT;
import com.google.gson.Gson;


public class Main2Activity extends AppCompatActivity {

    myDbAdapter helper;

    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ComponentName componentName;
    private static final int JOB_ID =101;
    AppDatabase mDb;
    ApiInterface apiInterface;
    String hsfJson;
    String transportJson;
    SharedPreferences myPref;
    SharedPreferences.Editor edit;
    ProgressBar progressBar;
    String staff_name,staff_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        myPref = getSharedPreferences("User_prefs", 0);
        edit = myPref.edit();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null) {
            staff_name = (String) b.get("staff_name");
            staff_id = (String) b.get("staff_id");

            edit.putString("CCOID", staff_id);
            edit.putString("CCOName", staff_name);
            edit.commit();
        }
        // End code stub

        // Background Sync

        /*componentName = new ComponentName(this, BackgroundSync.class);
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
        }*/
        requestPermissions();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 10);
        }
    }

    public void openScanScreen(View view) {

        edit.putString("OpenType", "OpenScan");
        edit.putString("ScanType", "Scan Warehouse");
        edit.commit();
        Intent i = new Intent(this,DefaultActivity.class );
        startActivity(i);

    }

    private void requestPermissions(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }

    }

    public void openFillScreen(View view) {


        edit.putString("OpenType", "OpenFill");
        edit.putString("ScanType", "Scan Warehouse");
        edit.commit();
        Intent i = new Intent(this,DefaultActivity.class );
        startActivity(i);
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

  /*  public void syncInventory(View view) {

        Snackbar.make(findViewById(android.R.id.content), "Sending data...\nYou will be notified when the process completes", Snackbar.LENGTH_SHORT).show();

        SyncData.SyncInventory syncTest = new SyncData.SyncInventory(getApplicationContext()){

            @Override
            protected void onPostExecute(String s){
                Toast.makeText(Main2Activity.this, s, Toast.LENGTH_LONG).show();
            }
        };

        syncTest.execute();

    }*/

    //Used in an onClick method here. Just add to your onCreate, etc....
   /* public void csvImport(View view){
        final ArrayList<inventoryT> invs = readCSVFromAssets(); //gets List of objects from readCSV method

        *//*
         * I am using Executors here. Your AsyncTask will also work.
        Will attach the Executors class also in case you need them.
         *//*
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
    }*/

    //CSV read into List of model class
    /*private ArrayList<inventoryT> readCSVFromAssets(){
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
                     Integer.parseInt(content[9]),
                        );
                inventoryTS.add(inv);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return inventoryTS; //return List
    }*/

    public void testCheck(View view){
        Message.message(getApplicationContext(), "Sync Started");



        final ArrayList<syncHSFResponse> syncHSFResponses = new ArrayList<>() ;
        progressBar = findViewById(R.id.pgBar);
        progressBar.setVisibility(View.VISIBLE);


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e){
                    Log.d("Except", e +"");
                }
                final inventoryT[] invss = mDb.inventoryTDao().selectUnsynced();
                final hsfTransportT[] hsfTransportTS = mDb.hsfTransportTDao().selectUnsynced();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hsfJson = new Gson().toJson(invss);
                        transportJson = new Gson().toJson(hsfTransportTS);
                        Log.d("Tobi", transportJson + "t");
                        if (hsfJson.length() < 5) {
                           // Message.message(getApplicationContext(), "No new HSF entries to sync");
                        } else {
                            //Start HSF Sync
                            Call<List<syncHSFResponse>> call = apiInterface.syncHSF(hsfJson);
                            call.enqueue(new Callback<List<syncHSFResponse>>() {
                                @Override
                                public void onResponse(Call<List<syncHSFResponse>> call, Response<List<syncHSFResponse>> response) {
                                    final List<syncHSFResponse> syncHSFResponses = response.body();
                                    Log.d("Tobi", "new " + response.body());
                                    int syncSize = syncHSFResponses.size();
                                    for (int i = 0; i < syncSize; i++) {
                                        if(i == syncSize-1){
                                            edit.putString("hsf_last_sync_time", syncHSFResponses.get(i).getSyncTime());
                                            edit.commit();
                                        }
                                        Log.d("Ayo", syncHSFResponses.get(i).getHsfId());
                                        final String hsf = syncHSFResponses.get(i).getHsfId();
                                        final int syncFlag = syncHSFResponses.get(i).getSyncFlag();
                                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDb.inventoryTDao().updateSyncFlag(hsf,syncFlag);
                                            }
                                        });
                                    }
                                   // Message.message(getApplicationContext(), "All HSF entries have been succesfully synced");
                                }

                                public void onFailure(Call<List<syncHSFResponse>> call, Throwable t) {
                                    Log.d("Tobi", t + "Tobi");
                                }
                            });

                        }

                        if (transportJson.length() < 5) {
                           // Message.message(getApplicationContext(), "No new Transport Payment entries to sync");
                            //progressBar.setVisibility(View.GONE);
                        } else {

                            // Sync Transport Payment table
                            Call<List<syncHSFResponse>> call2 = apiInterface.syncTransport(transportJson);
                            call2.enqueue(new Callback<List<syncHSFResponse>>() {
                                @Override
                                public void onResponse(Call<List<syncHSFResponse>> call, Response<List<syncHSFResponse>> response) {
                                    final List<syncHSFResponse> syncHSFResponses1 = response.body();
                                    Log.d("Tobi", "new " + response.body());
                                    int syncSize = syncHSFResponses1.size();
                                    for (int i = 0; i < syncSize; i++) {
                                        if(i == syncSize-1){
                                            edit.putString("transport_last_sync_time", syncHSFResponses1.get(i).getSyncTime());
                                            edit.commit();
                                        }
                                        Message.message(getApplicationContext(), syncHSFResponses1.get(i).getHsfId());
                                        Log.d("Ayo", syncHSFResponses1.get(i).getHsfId());
                                        final String hsf = syncHSFResponses1.get(i).getHsfId();
                                        final int syncFlag = syncHSFResponses1.get(i).getSyncFlag();
                                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDb.hsfTransportTDao().updateSyncFlag(hsf, syncFlag);
                                            }
                                        });
                                    }
                                    //Message.message(getApplicationContext(),"All new Transporter Payments successfully synced");

                                }

                                public void onFailure(Call<List<syncHSFResponse>> call, Throwable t) {
                                    Log.d("Tobi", t + "Tobi");
                                }
                            });
                        }

                        String dateP = "";
                        if (myPref.contains("msa_last_sync_time")) {
                            dateP = myPref.getString("msa_last_sync_time", "");
                        }else{
                            dateP = "2019-01-01 00:00:00";
                        }
                        Call<List<msaResponseT>> call3 = apiInterface.getMSAs(dateP);
                        call3.enqueue(new Callback<List<msaResponseT>>() {
                            @Override
                            public void onResponse(Call<List<msaResponseT>> call, Response<List<msaResponseT>> response) {
                                final List<msaResponseT> msaResponseTS = response.body();
                                Log.d("MsaTobi", "new " + response.body());
                                int syncSize = msaResponseTS.size();
                                for (int i = 0; i < syncSize; i++) {
                                    if (i == syncSize - 1) {
                                        edit.putString("msa_last_sync_time", msaResponseTS.get(i).getSyncTime());
                                        edit.commit();
                                    }
                                    final msaT msaT = new msaT(msaResponseTS.get(i).getStaffId(), msaResponseTS.get(i).getFullname(),
                                            msaResponseTS.get(i).getTemplate());
                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDb.msaTDao().insertMsa(msaT);
                                        }
                                    });
                                }
                                Message.message(getApplicationContext(), "All records successfully synced");
                                progressBar.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<List<msaResponseT>> call, Throwable t) {

                            }
                        });

                    }
                });


             }

        });


    }

    public void addWarehouse(View view) {
        SharedPreferences.Editor edit = myPref.edit();
        edit.putString("Activity", "Main2Activity.java");
        edit.commit();

        Intent i = new Intent(this, DefaultActivity.class);
        startActivity(i);

    }

    public void payTransporter(View view) {
        Intent j = new Intent(this, SelectMSA.class);
        startActivity(j);
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