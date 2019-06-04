package com.example.android.blindchat.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.android.blindchat.R;

public class TrendingFragment extends Fragment{

    private SearchView searchInTrending;
    private ConstraintLayout frame;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Fragment mPopularFragment;
    private Fragment mNewestFragment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, null);

        //to remove cursor from search bar
        frame = view.findViewById(R.id.fragment_trending);
        frame.requestFocus();

        searchInTrending = (SearchView)view.findViewById(R.id.search_in_trending);
        mTabLayout = view.findViewById(R.id.tl_tab_fragment_trending);
        mViewPager = view.findViewById(R.id.vp_room_list);

        //target string to search
        CharSequence query = searchInTrending.getQuery();

        searchInTrending.setIconifiedByDefault(false);
        searchInTrending.setQueryHint("Search");
        searchInTrending.setSubmitButtonEnabled(true);
        searchInTrending.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ((MainActivity)getActivity()).toSearchFragment();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new RoomListPagerAdapter(getChildFragmentManager()));

        return view;
    }


    protected class RoomListPagerAdapter extends FragmentPagerAdapter {
        public RoomListPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            switch(i) {
                case 0:
                    mPopularFragment = new PopularRoomFragment();
                    return mPopularFragment;
                case 1:
                    mNewestFragment = new NewestRoomFragment();
                    return mNewestFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Popular";
                case 1:
                    return "Newest";
                default:
                    return null;
            }
        }

    }
}
