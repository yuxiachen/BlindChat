package com.example.android.blindchat.ui;

import android.graphics.Color;
import android.os.Bundle;
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

public class SearchFragment extends Fragment {

    SearchView searchInSearch;
    ListView searchList;
    String[] searchListItems;
    LinearLayout frame;

    //fragmentStatePager for page change listener is needed
    //https://medium.com/@droidbyme/android-material-design-tabs-tab-layout-with-swipe-884085ae80ff

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);

        //to remove cursor from search bar
        frame = (LinearLayout)view.findViewById(R.id.fragment_search);
        frame.requestFocus();

        searchInSearch = (SearchView)view.findViewById(R.id.search_in_search);

        //target string to search
        CharSequence query = searchInSearch.getQuery();

        searchInSearch.setIconifiedByDefault(false);
        searchInSearch.setQueryHint("Search");
        searchInSearch.setSubmitButtonEnabled(true);
        searchInSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        searchList = (ListView)view.findViewById(R.id.list_search);
        searchListItems = getResources().getStringArray(R.array.array_search);
        ArrayAdapter<String> searchListAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,searchListItems);
        searchList.setAdapter(searchListAdapter);

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
