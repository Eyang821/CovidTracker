package au.edu.unsw.infs3634.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
//        DELETED ALL THIS
//        Button button = findViewById(R.id.btLaunchActivity);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launchDetailActivity("40735414-f500-4ba6-a05f-375fb7e93db8");
//            }
//        }); DELETED
    }

    private void launchDetailActivity(String message) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_MESSAGE, message);
        startActivity(intent);
    }
}