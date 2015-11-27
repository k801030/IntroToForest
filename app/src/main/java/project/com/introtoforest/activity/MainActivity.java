package project.com.introtoforest.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.astuetz.PagerSlidingTabStrip;

import project.com.introtoforest.R;
import project.com.introtoforest.fragment.PlayFragment;
import project.com.introtoforest.fragment.RankFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    PagerSlidingTabStrip mTabs;
    PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabs.setViewPager(mViewPager);

    }


    private class PagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = {"Play", "Rank"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PlayFragment();
                case 1:
                    return new RankFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
}
