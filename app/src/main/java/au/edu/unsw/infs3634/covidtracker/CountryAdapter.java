package au.edu.unsw.infs3634.covidtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    public ArrayList<Country> mCountries;
    private RecyclerViewClickListener mListener;

    public CountryAdapter(ArrayList<Country> countries, RecyclerViewClickListener listener) {
        mCountries = countries;
        mListener = listener;

    }

    public interface RecyclerViewClickListener {
        void onClick(View view, String id);
    }

    @NonNull
    @Override
    public CountryAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list, parent, false);
        return new CountryViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.CountryViewHolder holder, int position) {
        Country country = mCountries.get(position); //this takes in position so i want to get a country form my list of countries, i want t oget the one tht is relevant in the position of that recycler view. How i do that is: get(index from country list-
        holder.rvcountry.setText(country.getCountry());
        holder.rvTotalCases.setText(String.valueOf(country.getTotalConfirmed()));
        holder.rvNewCases.setText("+" + String.valueOf(country.getNewConfirmed()));
        holder.itemView.setTag(country.getId()); //make sure this is getId or the whole thing will fail!


    }

    @Override
    public int getItemCount() { //its going to tell the adapter how many items it iwll need to make viewholder for if return 0 in next line it says currently need to make 0.
//        return 0;
        return mCountries.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView rvcountry, rvTotalCases, rvNewCases;   //used to create each one of hte rows
        private CountryAdapter.RecyclerViewClickListener btlistener;

        public CountryViewHolder(@NonNull View itemView, CountryAdapter.RecyclerViewClickListener listener) {
            super(itemView);
            this.btlistener = listener;
            itemView.setOnClickListener(this);
            rvcountry = itemView.findViewById(R.id.rvCountry);
            rvTotalCases = itemView.findViewById(R.id.rvTotalCases);
            rvNewCases = itemView.findViewById(R.id.rvNewCases);
        }

        @Override
        public void onClick(View v) {
            btlistener.onClick(v, (String) v.getTag());
        }
    }
}