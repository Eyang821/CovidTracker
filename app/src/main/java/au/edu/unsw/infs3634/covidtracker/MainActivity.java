package au.edu.unsw.infs3634.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CountryAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rvlist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CountryAdapter.RecyclerViewClickListener mlistener = new CountryAdapter.RecyclerViewClickListener() { //this is our onClick method, when we click a method in our rv what we want to happen is written below
            @Override
            public void onClick(View view, String id) {
                launchDetailActivity(id); //we want to pass to detail activity this id so they know which one to open
            }
        };
        //no we need to initialise the adapter
        mAdapter = new CountryAdapter(Country.getCountries(), mlistener); //bc in other page we have constructor which passed in array and something else in other java file
        mRecyclerView.setAdapter(mAdapter);
    }

    private void launchDetailActivity(String message) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_MESSAGE, message);
        startActivity(intent);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        //first up you need a menu inflater
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newCases:
            mAdapter.sort(CountryAdapter.SORT_METHOD_NEW);
            return true;
            case R.id.totalCases:
                mAdapter.sort(CountryAdapter.SORT_METHOD_TOTAL);
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}