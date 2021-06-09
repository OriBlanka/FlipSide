package com.flipside;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsPagesCategory extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    MainAdapter adapter;
    int tabPosition;
    String selectedTabTitle = "ראשי";
    NewsFragment newsFrag = new NewsFragment();

    private static final String TAG = "NewsCategory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_pages_category);

        Bundle bundle = new Bundle();
        bundle.putString("categoryTitle", "ראשי");
        NewsFragment fragobj = new NewsFragment();
        fragobj.setArguments(bundle);

        tabLayout = findViewById(R.id.categoryTabLayout);
        viewPager = findViewById(R.id.newsViewPager);

        adapter = new MainAdapter(getSupportFragmentManager());

        adapter.addFragment(newsFrag, "ראשי");
        adapter.addFragment(new HelathNewsFragment(), "בריאות");
        adapter.addFragment(new EconomyNewsFragment(), "כלכלה");
        adapter.addFragment(new WorldNewsFragment(), "בעולם");
        adapter.addFragment(new SecurityNewsFragment(), "ביטחון");
        adapter.addFragment(new CultureNewsFragment(), "תרבות");
        adapter.addFragment(new SportsNewsFragment(), "ספורט");
        adapter.addFragment(new DigitalNewsFragment(), "דיגיטל וטכנולוגיה");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabPosition = tabLayout.getSelectedTabPosition();
        selectedTabTitle = adapter.getPageTitle(tabPosition).toString();
        Log.d(TAG, selectedTabTitle);
        Log.d(TAG, String.valueOf(tabPosition));


        //change the first selected tab style to bold
        Spannable wordtoSpan = new SpannableString(String.valueOf(selectedTabTitle));
        wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tabLayout.getTabAt(tabPosition).setText(wordtoSpan);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tabLayout.getSelectedTabPosition();
                Log.d(TAG, String.valueOf(tabPosition));
                selectedTabTitle = adapter.getPageTitle(tabPosition).toString();
                Log.d(TAG, selectedTabTitle);

                //change the selected tab style to bold
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);

                //newsFrag.refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //change the selected tab style to un-bold
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                wordtoSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(NewsPagesCategory.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
                //newsFrag.refresh();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }

    public String getCategoryName(){
        return selectedTabTitle;
    }

    private class MainAdapter extends FragmentPagerAdapter {

        ArrayList<String> stringArrayList = new ArrayList<>();
        List<Fragment> fragmentArrayList = new ArrayList<>();

        //Create constructor
        public void addFragment(Fragment fragment, String title) {
            //Add title
            stringArrayList.add(title);
            //Add fragment
            fragmentArrayList.add(fragment);
        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringArrayList.get(position);
        }
    }
}