package au.edu.unsw.infs3634.covidtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> implements Filterable {
    public ArrayList<Country> mCountries;
    private RecyclerViewClickListener mListener;
    private ArrayList<Country>mCountriesFiltered;
    public static final int SORT_METHOD_NEW = 1;
    public static final int SORT_METHOD_TOTAL = 2; //two ints

    public CountryAdapter(ArrayList<Country> countries, RecyclerViewClickListener listener) {
        mCountries = countries;
        mListener = listener;

    }
    @Override
    public Filter getFilter() {
        return new Filter() { // right click and it will give you methods to add in, just press enter
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                //have a string called charstring
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                } else {
                    ArrayList<Country> filteredList = new ArrayList<>();
                    for (Country country: mCountries) {
                        if (country.getCountry().toLowerCase().contains(charString.toLowerCase())){ //get Country and change it to lower case and see if contains this charsequence
                            filteredList.add(country);
                        }
                    }
                    mCountriesFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mCountriesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mCountriesFiltered = (ArrayList<Country>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
    public void sort(final int sortMethod){
        if(mCountriesFiltered.size()>0){
            Collections.sort(mCountriesFiltered, new Comparator<Country>() {
                @Override
                public int compare(Country c1, Country c2) {
                    if (sortMethod == SORT_METHOD_NEW) {
                        return c2.getNewConfirmed().compareTo(c2.getNewConfirmed());
                    } else if (sortMethod == SORT_METHOD_TOTAL) {
                        return c2.getTotalConfirmed().compareTo(c1.getTotalConfirmed());
                    }
                    return c2.getTotalConfirmed().compareTo(c1.getTotalConfirmed()); //a default option, returns that if default option occurs
                }
            });
        }
    notifyDataSetChanged();
    }
}