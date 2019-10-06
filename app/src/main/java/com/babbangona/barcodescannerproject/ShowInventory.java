package com.babbangona.barcodescannerproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.SearchManager;
import android.view.View;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.database.AppExecutors;
import com.babbangona.barcodescannerproject.model.inventoryT;

import java.util.ArrayList;
import java.util.List;


public class ShowInventory extends AppCompatActivity {
    myDbAdapter helper;
    private RecyclerView recycleView;
    String dateForList;
    BroadcastReceiver invShow_reciever;
    private List<inventoryT> inventoryTS = new ArrayList<>();
    AppDatabase mDb;
    private InventoryDateAdapter inventoryDateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent openShowInventory = getIntent();
        dateForList = openShowInventory.getStringExtra("Date_For_Inventory");
        mDb = AppDatabase.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_inventory);
        Toolbar toolbar = findViewById(R.id.toolbarDate);
        setSupportActionBar(toolbar);

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

        recycleView =  findViewById(R.id.recycleView);
        inventoryDateAdapter = new InventoryDateAdapter(inventoryTS);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(inventoryDateAdapter);
        recycleView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recycleView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycleView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final inventoryT inv = inventoryDateAdapter.getItem(position);
                AlertDialog.Builder optionCheck = new AlertDialog.Builder(ShowInventory.this);
                optionCheck.setMessage("Please select option");
                optionCheck.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent openEdit = new Intent(ShowInventory.this, EditRecordActivity.class);
                        openEdit.putExtra("ID_To_Edit", inv.getHSFID());
                        startActivity(openEdit);
                        finish();

                    }
                });
                optionCheck.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.inventoryTDao().deleteHSF(inv.getHSFID());
                                Intent i = new Intent(ShowInventory.this, Main2Activity.class);
                                startActivity(i);
                                finish();
                            }
                        });
                    }
                });
                AlertDialog dialog = optionCheck.create();
                dialog.show();



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                final List<inventoryT> inventoryTList = mDb.inventoryTDao().selectHSFForDate(dateForList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("Tola",inventoryDateAdapter.getItemCount() + "");
                        inventoryTS.clear();
                        inventoryTS.addAll(inventoryTList);
                        Log.d("Tobi", inventoryTS.size() + "");
                        //Log.d("Tobi", hsfTransportTList.size() + "");
                        inventoryDateAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        /*searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();*/
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                inventoryDateAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                inventoryDateAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        unregisterReceiver(invShow_reciever);
        super.onDestroy();
    }
}