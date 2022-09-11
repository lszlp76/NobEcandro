package com.lszlp.nobec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class Onboarding extends AppCompatActivity {
    public static ViewPager viewPager;
    SlideViewPagerAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewPager = findViewById(R.id.viewPager);
      adapter = new SlideViewPagerAdapter(this);
      viewPager.setAdapter(adapter);





    }
}