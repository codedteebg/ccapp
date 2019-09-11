package com.babbangona.barcodescannerproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String hsfid, String fieldid, int bagsMarketed, String seedType, String dateProcessed, String ccoName, String mold_count, String percent_clean, String percent_moisture, String kg_marketed)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.HSFID, hsfid);
        contentValues.put(myDbHelper.FieldID, fieldid);
        contentValues.put(myDbHelper.Bags, bagsMarketed);
        contentValues.put(myDbHelper.Seed, seedType);
        contentValues.put(myDbHelper.CCOName, ccoName);
        contentValues.put(myDbHelper.KG_Marketed, kg_marketed);

        // New Columns
        contentValues.put(myDbHelper.Mold_Count, mold_count);
        contentValues.put(myDbHelper.Percent_Clean, percent_clean);
        contentValues.put(myDbHelper.Percent_Moisture, percent_moisture);
        contentValues.put(myDbHelper.PROCESSED_DATE, dateProcessed);
        contentValues.put(myDbHelper.Upload_Status, 1);
        contentValues.put(myDbHelper.DELETED, 0);

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(dateProcessed));
            long dateP = calendar.getTimeInMillis();
            contentValues.put(myDbHelper.Date, dateP);
        }
        catch (Exception e) {}
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        dbb.close();
        return id;
    }

    public Cursor getData()
    {

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.HSFID,myDbHelper.FieldID,myDbHelper.Bags,myDbHelper.Seed,myDbHelper.Date,myDbHelper.CCOName
                ,myDbHelper.Mold_Count,myDbHelper.Percent_Clean,myDbHelper.Percent_Moisture,myDbHelper.KG_Marketed,myDbHelper.Upload_Status};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,myDbHelper.DELETED+"!=1",null,null,null,null);

        return cursor;
    }

    public HashMap<String, String> getMoldCount(Context context, double value, String selectedSeedSummary)
    {
        HashMap<String, String> totalCropBagsNo= new HashMap<String, String>();
        int high=0, low=0;
        SQLiteDatabase database = new myDbAdapter.myDbHelper(context).getReadableDatabase();

        Cursor highMoldCursor = database.rawQuery("SELECT COUNT(*) FROM inventoryT WHERE SeedType = \""+selectedSeedSummary+"\" AND  (mold_count*1.0) > "+value+" AND deleted !=1",null);
        Cursor lowMoldCursor = database.rawQuery("SELECT COUNT(*) FROM inventoryT WHERE SeedType = \""+selectedSeedSummary+"\" AND (mold_count*1.0) <= "+value+" AND deleted !=1", null);

        if (highMoldCursor.moveToFirst()) {
            high = highMoldCursor.getInt(0);
            totalCropBagsNo.put("high", high+"");
        }
        if (lowMoldCursor.moveToFirst()) {
            low = lowMoldCursor.getInt(0);
            totalCropBagsNo.put("low", low+"");
        }
        return totalCropBagsNo;
    }

    public HashMap<String, String> getPercentClean(Context context, double value, String selectedSeedSummary)
    {
        HashMap<String, String> totalCropBagsNo= new HashMap<String, String>();
        int high=0, low=0;
        SQLiteDatabase database = new myDbAdapter.myDbHelper(context).getReadableDatabase();

        Cursor highMoldCursor = database.rawQuery("SELECT COUNT(*) FROM inventoryT WHERE SeedType = ? AND deleted !=1 AND  (percent_clean*1.0) >= "+value, new String[] {selectedSeedSummary});
        Cursor lowMoldCursor = database.rawQuery("SELECT COUNT(*) FROM inventoryT WHERE SeedType = ? AND deleted !=1 AND (percent_clean*1.0) < "+value, new String[] {selectedSeedSummary});

        if (highMoldCursor.moveToFirst()) {
            high = highMoldCursor.getInt(0);
            totalCropBagsNo.put("high", high+"");
        }
        if (lowMoldCursor.moveToFirst()) {
            low = lowMoldCursor.getInt(0);
            totalCropBagsNo.put("low", low+"");
        }
        return totalCropBagsNo;
    }

    public HashMap<String, String> getPercentMoisture(Context context, double value, String selectedSeedSummary)
    {
        HashMap<String, String> totalCropBagsNo= new HashMap<String, String>();
        int high=0, low=0;
        SQLiteDatabase database = new myDbAdapter.myDbHelper(context).getReadableDatabase();

        Cursor highMoldCursor = database.rawQuery("SELECT COUNT(*) FROM inventoryT WHERE SeedType = ? AND deleted !=1 AND (percent_moisture*1.0) >= "+value, new String[] {selectedSeedSummary});
        Cursor lowMoldCursor = database.rawQuery("SELECT COUNT(*) FROM inventoryT WHERE SeedType = ? AND deleted !=1 AND (percent_moisture*1.0) < "+value, new String[] {selectedSeedSummary});

        if (highMoldCursor.moveToFirst()) {
            high = highMoldCursor.getInt(0);
            totalCropBagsNo.put("high", high+"");
        }
        if (lowMoldCursor.moveToFirst()) {
            low = lowMoldCursor.getInt(0
            );
            totalCropBagsNo.put("low", low+"");
        }
        return totalCropBagsNo;
    }

    public ArrayList<Map<String, String>> getDataSync()
    {
        Map<String, String> map = null;
        ArrayList<Map<String, String>> wordList;
        wordList =new ArrayList();

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.HSFID,myDbHelper.FieldID,myDbHelper.Bags,myDbHelper.Seed,myDbHelper.Date,myDbHelper.CCOName
                ,myDbHelper.Mold_Count,myDbHelper.Percent_Clean,myDbHelper.Percent_Moisture,myDbHelper.KG_Marketed,myDbHelper.DELETED,myDbHelper.PROCESSED_DATE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,myDbHelper.Upload_Status+"=1",null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                map = new HashMap<String, String>();
                map.put("_id",cursor.getString(0));
                map.put("HsfID",cursor.getString(1));
                map.put("FieldID",cursor.getString(2));
                map.put("BagsMarketed",cursor.getString(3));
                map.put("SeedType",cursor.getString(4));
                map.put("Date",cursor.getString(5));
                map.put("CCOName",cursor.getString(6));
                map.put("mold_count",cursor.getString(7));
                map.put("percent_clean",cursor.getString(8));
                map.put("percent_moisture",cursor.getString(9));
                map.put("kg_marketed",cursor.getString(10));
                map.put("deleted",cursor.getString(11));
                map.put("processed_date",cursor.getString(12));
                wordList.add(map);
                System.out.println("Wordlist: "+wordList);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }
        cursor.close();
        return wordList;
    }

    public int updateRecordSync(Context context)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(myDbHelper.HSFID,upHsf);
        contentValues.put(myDbHelper.Upload_Status, 0);

        int count =db.update(myDbHelper.TABLE_NAME,contentValues, null, null );

        // Delete deleted records locally
        db.delete(myDbHelper.TABLE_NAME, myDbHelper.DELETED+"=1", null);

        return count;

    }

    public int updateRecord(int id, String upHsf, String upFieldID, int upBags, String upSeed, String upDate, String mold_count, String percent_clean, String percent_moisture, String kg_marketed)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.HSFID,upHsf);
        contentValues.put(myDbHelper.FieldID, upFieldID);
        contentValues.put(myDbHelper.Bags, upBags);
        contentValues.put(myDbHelper.Seed, upSeed);

        // New Columns
        contentValues.put(myDbHelper.Mold_Count, mold_count);
        contentValues.put(myDbHelper.Percent_Clean, percent_clean);
        contentValues.put(myDbHelper.Percent_Moisture, percent_moisture);
        contentValues.put(myDbHelper.KG_Marketed, kg_marketed);
        contentValues.put(myDbHelper.Upload_Status, 1);

        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(upDate));
            long dateU = calendar.getTimeInMillis();
            contentValues.put(myDbHelper.Date, dateU);
        }
        catch (Exception e) {}

        String upId = String.valueOf(id);
        String[] whereArgs= {upId};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.UID+" = ?",whereArgs );
        return count;

    }

    public static class myDbHelper extends SQLiteOpenHelper
    {
        public static final String DATABASE_NAME = "inventoryDB";    // Database Name
        public static final String TABLE_NAME = "inventoryT";   // Table Name
        public static final int DATABASE_Version = 1;    // Database Version
        public static final String UID="_id";     // Column I (Primary Key)
        public static final String HSFID="HsfID"; // Column II
        public static final String FieldID="FieldID"; //Column III
        public static final String Bags="BagsMarketed"; //Column IV
        public static final String Seed="SeedType"; //Column V
        public static final String Date="Date"; //Column VI
        public static final String CCOName= "CCOName";    // Column III

        //New columns
        public static final String Mold_Count = "mold_count";
        public static final String Percent_Clean = "percent_clean";
        public static final String Percent_Moisture = "percent_moisture";
        public static final String Upload_Status = "upload_status";
        public static final String KG_Marketed = "kg_marketed";
        public static final String DELETED = "deleted";
        public static final String PROCESSED_DATE = "processed_date";

        public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+HSFID+" VARCHAR(255),"+ FieldID+" VARCHAR(225),"+ Bags+
                " INTEGER ,"+ Seed+" VARCHAR(255)," +Date+" INTEGER ," +CCOName+" VARCHAR(255),"
                +Mold_Count+" VARCHAR(25)," +Percent_Clean+" VARCHAR(25)," +Percent_Moisture+" VARCHAR(25)," +KG_Marketed+" VARCHAR(25),"
                +Upload_Status+" INTEGER, "+DELETED+" INTEGER," +PROCESSED_DATE+" VARCHAR(20), CONSTRAINT hsf_unique UNIQUE("+HSFID+"));";
        public static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        public Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {

                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }*/
        }
    }
}
