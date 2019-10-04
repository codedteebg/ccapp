package com.babbangona.barcodescannerproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.babbangona.barcodescannerproject.database.AppDatabase;
import com.babbangona.barcodescannerproject.database.AppExecutors;
import com.babbangona.barcodescannerproject.model.hsf;
import com.babbangona.barcodescannerproject.model.hsfTransportT;

import java.util.ArrayList;
import java.util.List;

public class SelectHSFForTransport extends AppCompatActivity {
    private List<hsf> hsfTransportTList = new ArrayList<>();
    private RecyclerView hsfRecycler;
    private HSFTransportAdapter hsfTransportAdapter;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hsffor_transport);

        Toolbar toolbar = findViewById(R.id.toolbarHSF);
        setSupportActionBar(toolbar);

        mDb = AppDatabase.getInstance(getApplicationContext());

        hsfRecycler = findViewById(R.id.hsfRecyclerview);
        hsfTransportAdapter = new HSFTransportAdapter(hsfTransportTList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        hsfRecycler.setLayoutManager(mLayoutManager);
        hsfRecycler.setItemAnimator(new DefaultItemAnimator());
        hsfRecycler.setAdapter(hsfTransportAdapter);
        hsfRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        hsfRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), hsfRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                hsf hsf = hsfTransportAdapter.getItem(position);
                Message.message(getApplicationContext(), hsf.getHSFID() + "is selected");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareHSFData();

    }

    private void prepareHSFData(){

       // final List<hsfTransportT> hsfTransportTS = new ArrayList<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                hsfTransportTTList = mDb.inventoryTDao().selectUnpaidTransport();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Tobi", hsfTransportTList.size() + "");
                        Log.d("Tola",hsfTransportAdapter.getItemCount() + "");
                        hsfTransportTList = hsf
                        hsfTransportAdapter.notifyDataSetChanged();
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
                hsfTransportAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                hsfTransportAdapter.getFilter().filter(query);
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
