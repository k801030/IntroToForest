package project.com.introtoforest.activity;

import android.content.Intent;
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
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

import project.com.introtoforest.R;
import project.com.introtoforest.fragment.PlayFragment;
import project.com.introtoforest.fragment.RankFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    PagerSlidingTabStrip mTabs;
    PagerAdapter mPagerAdapter;
    Button mStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mStartBtn = (Button) findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

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
