package com.babbangona.barcodescannerproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babbangona.barcodescannerproject.databinding.InventoryListViewBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecyclerViewCursorAdapter extends RecyclerView.Adapter<RecyclerViewCursorAdapter.ViewHolder> {

    Context mContext;
    Cursor mCursor;
    String dateP;

    public RecyclerViewCursorAdapter(Context context, Cursor cursor) {

        mContext = context;
        mCursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        InventoryListViewBinding itemBinding;
        myDbAdapter helper;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBinding = DataBindingUtil.bind(itemView);
            helper = new myDbAdapter(mContext);

            itemBinding.editRecord.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String uid = itemBinding.idLabel.getText().toString();
                    if (uid.isEmpty()){
                        Snackbar.make(v, "ID is empty.",
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Intent openEditRecords = new Intent(v.getContext(), EditRecordActivity.class);
                        openEditRecords.putExtra("ID_To_Edit", uid);
                        v.getContext().startActivity(openEditRecords);
                    }
                }
            });

                itemBinding.deleteRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uid = itemBinding.idLabel.getText().toString();
                        if (uid.isEmpty()) {
                            Snackbar.make(v, "ID is empty.",
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            SQLiteDatabase database = new myDbAdapter.myDbHelper(mContext).getWritableDatabase();
                            String whereClause = "_id=?";
                            String[] whereArgs = {uid};

                            //int delcount = database.delete(TABLE_NAME, whereClause, whereArgs);

                            ContentValues contentValues = new ContentValues();
                            //contentValues.put(myDbHelper.HSFID,upHsf);
                            contentValues.put(myDbAdapter.myDbHelper.DELETED, 1);
                            contentValues.put(myDbAdapter.myDbHelper.Upload_Status, 1);

                            int count = database.update(myDbAdapter.myDbHelper.TABLE_NAME, contentValues, whereClause, whereArgs);
                            if (count <= 0) {
                                Snackbar.make(v, "Unsuccessful",
                                        Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(v, "Successful",
                                        Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                Intent leaveSummaryPage = new Intent(v.getContext(), MainActivity.class);
                                v.getContext().startActivity(leaveSummaryPage);
                            }

                        }
                    }
                });

        }

        public void bindCursor(Cursor cursor) {
            itemBinding.idLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.UID)
            ));

            dateP = cursor.getString(
                    cursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Date));

            System.out.println("Law: "+dateP);



            // hide update and delete button if date > 172800000

            String date2 = "";
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date dates = new Date();
            String date1 = format.format(dates);

            Calendar calendar1 = Calendar.getInstance();
            try {
                calendar1.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(date1));
                date2 = String.valueOf((calendar1.getTimeInMillis()-172800000));
            }catch (Exception e){}

            if(date2.compareTo(dateP) > 0){
                itemBinding.deleteRecord.setVisibility(View.GONE);
                itemBinding.editRecord.setVisibility(View.GONE);
            }


            itemBinding.hsfIDLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.HSFID)
            ));

            itemBinding.FieldIDLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.FieldID)
            ));

            itemBinding.bagsMarketedLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Bags)
            ));

            itemBinding.seedTypeLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Seed)
            ));
            itemBinding.processedByLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.CCOName)
            ));

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(
                    cursor.getColumnIndexOrThrow(myDbAdapter.myDbHelper.Date)));
            itemBinding.dateProcessedLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()
            ));
        }
    }

    @Override
    public int getItemCount(){
        return mCursor.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        mCursor.moveToPosition(position);
        holder.bindCursor(mCursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

         View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.inventory_list_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}