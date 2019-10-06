package com.babbangona.barcodescannerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityy_main);
    }

    public void openAccess(View view){
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.babbangona.accesscontrol", "com.babbangona.accesscontrol.MainActivity"));
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this, "You have not installed bg store", Toast.LENGTH_SHORT).show();
        }
    }
}
