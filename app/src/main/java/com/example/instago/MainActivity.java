package com.example.instago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;    //getting access of the tab layout
    TabItem mPhoto, mVideo, mReel, mIgtv, mProfilePic;      //main activity tabs
    Toolbar toolbar;
    PagerAdapter pagerAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPhoto = findViewById(R.id.photoTab);
        mVideo = findViewById(R.id.videoTab);
        mReel = findViewById(R.id.reelTab);
        mIgtv = findViewById(R.id.igtvTab);
        mProfilePic = findViewById(R.id.profilepicTab);


        ViewPager viewPager = findViewById(R.id.fragmentContainer);
        tabLayout = findViewById(R.id.include);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 5);
        viewPager.setAdapter(pagerAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2 || tab.getPosition() == 3 || tab.getPosition() == 4) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));






    }
}