package com.babbangona.barcodescannerproject;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class BackgroundSync extends JobService{

    SyncData.AppLastUsedDate downloadApplication;
    SyncData.SyncInventory syncApplication;

    @SuppressLint("StaticFieldLeak")

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        downloadApplication = new SyncData.AppLastUsedDate(getApplicationContext()){

            @Override
            protected void onPostExecute(String s){
                //Toast.makeText(mss_page.this, s, Toast.LENGTH_SHORT).show();
                if(s!= null && s.trim().equalsIgnoreCase("done")){
                    //Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show();
                    jobFinished(jobParameters,true);

                    //startActivity(intent);

                }else if(s!= null && s.trim().equalsIgnoreCase("not inserted")){
                    //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    jobFinished(jobParameters,true);
                }else{
                    Log.d("ACTIVITY_SYNC",s);
                    // Toast.makeText(context, "Error: Couldnt communicate with the online database", Toast.LENGTH_SHORT).show();
                    jobFinished(jobParameters,true);
                }
            }
        };

        downloadApplication.execute();

        syncApplication = new SyncData.SyncInventory(getApplicationContext()){

            @Override
            protected void onPostExecute(String s){
                if(s!= null && s.trim().equalsIgnoreCase("done")){
                    jobFinished(jobParameters,true);
                }else if(s!= null && s.trim().equalsIgnoreCase("not inserted")){
                    //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    jobFinished(jobParameters,true);
                }else{
                    Log.d("ACTIVITY_SYNC",s);
                    // Toast.makeText(context, "Error: Couldnt communicate with the online database", Toast.LENGTH_SHORT).show();
                    jobFinished(jobParameters,true);
                }
            }
        };

        syncApplication.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        //downloadApplication.cancel(true);
        return false;
    }
}