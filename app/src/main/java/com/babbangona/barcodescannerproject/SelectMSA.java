package com.babbangona.barcodescannerproject;


import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;

import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.database.AppExecutors;
import com.babbangona.barcodescannerproject.model.msaT;

import java.util.ArrayList;
import java.util.List;

public class SelectMSA extends AppCompatActivity {
    private List<msaT> msaTList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MsaAdapter msaAdapter;
    private SharedPreferences myPref;
    private SearchView searchView;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_msa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myPref = getSharedPreferences("User_prefs", 0);
        mDb = AppDatabase.getInstance(getApplicationContext());



        recyclerView = findViewById(R.id.msaRecyclerview);
        msaAdapter = new MsaAdapter(msaTList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(msaAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //msaT msaT = msaTList.get(position);
                SharedPreferences.Editor edit = myPref.edit();
                msaT msaT = msaAdapter.getItem(position);
                Message.message(getApplicationContext(), msaT.getStaff_id() + "is selected");
                edit.putString("MSAID",msaT.getStaff_id());
                edit.commit();
                Intent i = new Intent(SelectMSA.this, SelectHSFForTransport.class);
                startActivity(i);
               // CallLuxandPrefs(msaT.getStaff_id(), msaT.getFacetemplate());
            }

            @Override
            public void onLongClick(View view, int position) {
                msaT msaT = msaAdapter.getItem(position);
                Message.message(getApplicationContext(), msaT.getStaff_id() + "is selected");
            }
        }));

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                final List<msaT> msaTS = mDb.msaTDao().selectMSAS();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //Log.d("Tola",hsfTransportAdapter.getItemCount() + "");
                        msaTList.clear();
                        msaTList.addAll(msaTS);
                        //Log.d("Tobi", hsfTransportTList.size() + "");
                        msaAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


    }

   /* public void CallLuxandPrefs(String staff_id, String template) {
        SharedPreferences.Editor editor = myPref.edit();
        editor.putString("msaStaff", staff_id);
        editor.commit();
        Intent LuxandIntent = new Intent(this, LuxandAuthActivity.class);
        LuxandIntent.putExtra("PERSON", staff_id);
        LuxandInfo luxandInfo = new LuxandInfo(this);
        luxandInfo.putTemplate(template);
        luxandInfo.putMessage("DO NOT PRESS BACK UNTIL FACE IS DONE CAPTURING");
        startActivityForResult(LuxandIntent, 419);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 419 && data != null) {
            if (data.getIntExtra("RESULT", 0) == 1) {
                Message.message(getApplicationContext(), "Face Authenticated");
                Intent i = new Intent(SelectMSA.this, SelectHSFForTransport.class);
                startActivity(i);
            } else {
                Message.message(getApplicationContext(), "Face Not Authenticated");
            }
        }
    }*/

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
                msaAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                msaAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
