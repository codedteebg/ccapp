package com.babbangona.barcodescannerproject;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class DefaultActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    String fieldID, bags, seedDistributed, hsfID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        //setTitle("Scan");

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 5);
        }
    }

    public void QrScanner(View view){
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera
    }


    @Override
    public void handleResult(Result rawResult) {

        final String format = rawResult.getBarcodeFormat().toString();

        if(format.equals("QR_CODE")) {

            String lastChars = rawResult.getText().substring(rawResult.getText().length() - 5);

            if (lastChars.equalsIgnoreCase("FREE*")) {


                // Do something with the result here
                String checkFieldLoc = "var1";
                String checkBagsStartLoc = "var2s", checkBagsEndLoc = "var2e";
                String checkSeedStartLoc = "var3s", checkSeedEndLoc = "var3e";
                String checkHsfIDStartLoc = "var4s", checkHsfIDEndLoc = "var4e";
                final int fieldNum, BagsStartNum, BagsEndNum, SeedStartNum, SeedEndNum, HsfIDStartNum, HsfIDEndNum;
                Log.e("handler", rawResult.getText()); // Prints scan results
                Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
                fieldNum = rawResult.getText().indexOf(checkFieldLoc);
                fieldID = rawResult.getText().substring(10, fieldNum);
                BagsStartNum = rawResult.getText().indexOf(checkBagsStartLoc) + 5;
                BagsEndNum = rawResult.getText().indexOf(checkBagsEndLoc);
                bags = rawResult.getText().substring(BagsStartNum, BagsEndNum);
                SeedStartNum = rawResult.getText().indexOf(checkSeedStartLoc) + 5;
                SeedEndNum = rawResult.getText().indexOf(checkSeedEndLoc);

                seedDistributed = rawResult.getText().substring(SeedStartNum, SeedEndNum);
                // Map here
                Master map = new Master();
                seedDistributed = map.getSeedType(seedDistributed);
                // Map ends

                HsfIDStartNum = rawResult.getText().indexOf(checkHsfIDStartLoc) + 5;
                HsfIDEndNum = rawResult.getText().indexOf(checkHsfIDEndLoc);
                hsfID = rawResult.getText().substring(HsfIDStartNum, HsfIDEndNum);


                // show the scanner result into dialog box.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Scan Result");
                builder.setMessage("FieldID = " + fieldID + "\n BagsMarketed = " + bags + "\n SeedDistributed = " + seedDistributed);
                builder.setCancelable(true);
                final AlertDialog alert1 = builder.create();
                alert1.show();


                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        alert1.dismiss(); // when the task active then close the dialog
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                        mScannerView.stopCamera();

                        Intent intent = new Intent(DefaultActivity.this, SecondScanActivity.class);
                        intent.putExtra("HSF_ID", hsfID);
                        intent.putExtra("FIELD_ID", fieldID);
                        intent.putExtra("BAGS_MKTD", bags);
                        intent.putExtra("SEED_TYPE", seedDistributed);
                        startActivity(intent);
                        finish();

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


