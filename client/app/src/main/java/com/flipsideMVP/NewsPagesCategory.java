package com.flipsideMVP;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsPagesCategory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TabLayout tabLayout;
    ViewPager viewPager;
    MainAdapter adapter;
    int tabPosition;
    String selectedTabTitle = "ראשי";
    NewsFragment newsFrag = new NewsFragment();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private static final String TAG = "NewsCategory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_pages_category);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        tabLayout = findViewById(R.id.categoryTabLayout);
        viewPager = findViewById(R.id.newsViewPager);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.newsViewPager,
                    new NewsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_main);
        }


        adapter = new MainAdapter(getSupportFragmentManager());

        adapter.addFragment(newsFrag, "ראשי");
        adapter.addFragment(new SecurityNewsFragment(), "ביטחוני");
        adapter.addFragment(new PoliticalNewsFragment(), "פוליטי");
        adapter.addFragment(new EconomyNewsFragment(), "כלכלי");
        adapter.addFragment(new WorldNewsFragment(), "בעולם");
        adapter.addFragment(new HelathNewsFragment(), "בריאות");
        adapter.addFragment(new CultureNewsFragment(), "תרבות");
        adapter.addFragment(new SportsNewsFragment(), "ספורט");


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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_source:
                getSupportFragmentManager().beginTransaction().replace(R.id.newsViewPager,
                        new SourceFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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