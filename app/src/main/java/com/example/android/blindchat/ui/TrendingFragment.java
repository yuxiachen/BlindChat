package com.example.android.blindchat.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.android.blindchat.R;

public class TrendingFragment extends Fragment {

    SearchView searchInTrending;
    ListView mostPopularList;
    String[] mostPopularListItems;
    ListView newestList;
    String[] newestListItems;
    LinearLayout frame;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, null);

        //to remove cursor from search bar
        frame = (LinearLayout)view.findViewById(R.id.fragment_trending);
        frame.requestFocus();

        searchInTrending = (SearchView)view.findViewById(R.id.search_in_trending);

        //target string to search
        CharSequence query = searchInTrending.getQuery();

        searchInTrending.setIconifiedByDefault(false);
        searchInTrending.setQueryHint("Search");
        searchInTrending.setSubmitButtonEnabled(true);
        searchInTrending.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                toSearchFragment();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mostPopularList = (ListView)view.findViewById(R.id.list_most_popular);
        mostPopularListItems = getResources().getStringArray(R.array.array_most_popular);
        ArrayAdapter<String> mpListAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,mostPopularListItems);
        mostPopularList.setAdapter(mpListAdapter);

        newestList = (ListView)view.findViewById(R.id.list_newest);
        newestListItems = getResources().getStringArray(R.array.array_newest);
        ArrayAdapter<String> newListAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,newestListItems);
        newestList.setAdapter(newListAdapter);



        return view;
    }

    private void toSearchFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        transaction.replace(R.id.main_frame, searchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
