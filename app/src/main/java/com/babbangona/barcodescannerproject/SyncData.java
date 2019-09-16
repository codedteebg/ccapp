package com.babbangona.barcodescannerproject;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SyncData {


    static String URL = "http://apps.babbangona.com/collection_center/";
    @SuppressLint("StaticFieldLeak")
    public static class SyncInventory extends AsyncTask<Void,Void,String> {

        Context context;
        ArrayList wordlist;

        myDbAdapter helper;

        String staff_id;

        public SyncInventory(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected String doInBackground(Void... voids) {

            helper = new myDbAdapter(context);
            wordlist = helper.getDataSync();

            String versionNo="1";

            if(wordlist.size()<1) return "Your online database is already updated";

            Gson gson = new GsonBuilder().create();
            String WordList = gson.toJson(wordlist);

            HttpURLConnection httpURLConnection = null;

            try {
                URL url = new URL(URL+"sync_inventory.php");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("wordlist", "UTF-8") + "=" + URLEncoder.encode(WordList, "UTF-8")+"&"+
                        URLEncoder.encode("versionNo", "UTF-8") + "=" + URLEncoder.encode(versionNo, "UTF-8");

                bufferedWriter.write(data_string); // writing information to Database
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpURLConnection.connect();

            } catch (IOException e) {e.printStackTrace();}

            try{
                int response_code = httpURLConnection.getResponseCode();
                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream input = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {result.append(line);}

                    if(result.toString().equalsIgnoreCase("done")){
                        // update status
                        helper.updateRecordSync(context);
                    }
                    return result.toString();
                } else{return("connection error");}
            }

            catch (IOException e){
                e.printStackTrace(); return "Sync failed due to internal error. Most likely a network error";
            }
            finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }

    }

    public static class refreshUsername extends AsyncTask<Void,Void,String> {

        Context context;

        myDbAdapter helper;

        public refreshUsername(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected String doInBackground(Void... voids) {

            helper = new myDbAdapter(context);

            HttpURLConnection httpURLConnection = null;

            try {
                URL url = new URL(URL+"fetch_username.php");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpURLConnection.connect();

            } catch (IOException e) {e.printStackTrace();}

            try{
                int response_code = httpURLConnection.getResponseCode();
                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream input = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {result.append(line);}


                    try {
                        JSONArray JA = new JSONArray(result.toString());
                        SQLiteDatabase mDb;
                        mDb = new DatabaseHelper(context).getReadableDatabase();

                        JSONObject json = null;
                        String id, employeeName, password;
                        String Insert_Query, delete;

                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);
                            id = json.getString("id");
                            employeeName = json.getString("employeeName");
                            password = json.getString("password");
                            delete = json.getString("delete");
                            try {
                                if(!delete.equalsIgnoreCase("0")){
                                    Insert_Query = "DELETE FROM employeeT WHERE _id = '" + id + "'";
                                    Log.d("query", Insert_Query);
                                    mDb.execSQL(Insert_Query);
                                }else {
                                    Insert_Query = "INSERT INTO employeeT(_id, employeeName,password) VALUES('" + id + "','" + employeeName + "','" +
                                            password + "')";
                                    Log.d("query", Insert_Query);
                                    mDb.execSQL(Insert_Query);
                                }
                            }catch (Exception e){
                                Insert_Query = "UPDATE employeeT SET employeeName='" + employeeName + "', password = '" + password +"' WHERE _id ='" + id + "'";
                                Log.d("query", Insert_Query);
                                mDb.execSQL(Insert_Query);
                            }
                        }
                        return "done";



                        //Toast.makeText(context, x, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Log.e("Fail 3", e.toString());
                    }

                    return "done";
                } else{return("connection error");}
            }

            catch (IOException e){
                e.printStackTrace(); return "Sync failed due to internal error. Most likely a network error";
            }
            finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }

    }

    public static class AppLastUsedDate extends AsyncTask<String,Void,String> {

        Context context;


        public AppLastUsedDate(Context mCtx) {this.context = mCtx;}

        @Override
        protected String doInBackground(String... params) {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

            String dates = dateFormat.format(date);

            HttpURLConnection httpURLConnection = null;

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String staff_id = preferences.getString("Name", "");

            try {
                java.net.URL url = new URL(URL+"/last_used.php");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("package_name", "UTF-8") + "=" + URLEncoder.encode("com.babbangona.barcodescannerproject", "UTF-8")+"&"+
                        URLEncoder.encode("staff_id", "UTF-8") + "=" + URLEncoder.encode(staff_id, "UTF-8")+"&"+
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(dates, "UTF-8");

                bufferedWriter.write(data_string); // writing information to Database
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpURLConnection.connect();

            } catch (IOException e) {e.printStackTrace();}

            try{
                int response_code = httpURLConnection.getResponseCode();
                if(response_code == HttpURLConnection.HTTP_OK){
                    InputStream input = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {result.append(line);}
                    return (result.toString());
                } else{return("connection error");}
            }

            catch (IOException e){e.printStackTrace(); return e.toString();}
            finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }

    }


}
