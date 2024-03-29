package com.babbangona.barcodescannerproject;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class DefaultActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    //String fieldID, bags, seedDistributed, hsfID;
    SharedPreferences myPref;
    String openType, scanType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        //setTitle("Scan");
        myPref = getSharedPreferences("User_prefs", 0);
        openType = myPref.getString("OpenType", "");
        scanType = myPref.getString("ScanType", "");

        setTitle(scanType);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 5);
        }

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();
    }

  /*  public void QrScanner(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }*/


    @Override
    public void handleResult(final Result rawResult) {

        final String format = rawResult.getBarcodeFormat().toString();
        final String Result;
        final SharedPreferences.Editor edit = myPref.edit();

        if(format.equals("QR_CODE")) {

            String lastChars = rawResult.getText().substring(rawResult.getText().length() - 5);

            if (lastChars.equalsIgnoreCase("FREE*")) {
                if(rawResult.getText().length() > 23){
                     Result = rawResult.getText().substring(0, rawResult.getText().length() - 23);
                }
                else Result = rawResult.getText();


                // Do something with the result here

                // show the scanner result into dialog box.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Scan Result");
                //builder.setMessage("FieldID = " + fieldID + "\n BagsMarketed = " + bags + "\n SeedDistributed = " + seedDistributed);
                builder.setMessage(Result);
                builder.setCancelable(true);
                final AlertDialog alert1 = builder.create();
                alert1.show();


                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        alert1.dismiss(); // when the task active then close the dialog
                        t.cancel(); // also just stop the timer thread, otherwise, you may receive a crash report
                        mScannerView.stopCamera();

                        if (openType.equalsIgnoreCase("OpenScan") && scanType.equalsIgnoreCase("Scan Warehouse")) {
                            if (Result.matches("(.*)NG(.*)")) {
                                edit.putString("ScanType", "Scan HSF");
                                final String[] Results = Result.split(",");
                                edit.putString("Warehouse", Results[1]);
                                edit.commit();

                                Intent i = new Intent(DefaultActivity.this, DefaultActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message.message(getApplicationContext(), "Wrong Warehouse QR Code Scanned");
                                    }
                                });

                                Intent i = new Intent(DefaultActivity.this, Main2Activity.class);
                                startActivity(i);
                                finish();
                            }
                        } else if(openType.equalsIgnoreCase("OpenScan" )&& scanType.equalsIgnoreCase("Scan HSF")){
                            if(Result.matches("HS(.*)")){
                                Log.d("Tola", "Got here" + Result);
                                Intent i = new Intent(DefaultActivity.this, SecondScanActivity.class);
                                i.putExtra("SCAN_RESULT", Result);
                                startActivity(i);
                                finish();
                            } else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message.message(getApplicationContext(),"Wrong HSF QR Code Scanned");
                                    }
                                });

                                Intent i = new Intent(DefaultActivity.this, Main2Activity.class);
                                startActivity(i);
                                finish();
                            }
                           // Message.message(getApplicationContext(), Result);

                        } else if(openType.equalsIgnoreCase("OpenFill") && scanType.equalsIgnoreCase("Scan Warehouse")){
                            final String[] Results = Result.split(",");
                            edit.putString("Warehouse", Results[1]);
                            edit.commit();
                            Intent i = new Intent(DefaultActivity.this, FillActivity.class);
                            startActivity(i);
                        }




                    }
                }, 1000); // after 1 second (or 1000 miliseconds), the task will be active.

            }
            else {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("Error");
                builder2.setMessage("Wrong QR Code Results");
                builder2.setCancelable(true);
                final AlertDialog alert2 = builder2.create();
                alert2.show();
            }


            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Error");
                builder1.setMessage("Wrong QR_Code format");
                builder1.setCancelable(true);
                final AlertDialog alert2 = builder1.create();
                alert2.show();
            }
        }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mScannerView != null){
            mScannerView.stopCamera();
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mScannerView != null) {
                        mScannerView.stopCamera();
                }
                finish();
                break;
        }
        return true;
    }

    /*@Override
    public void onPause() {
         super.onPause();
         mScannerView.stopCamera();// Stop camera on pause
     }*/



        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);
    }


