package com.lszlp.nobec;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.theme.MaterialComponentsViewInflater;

import java.util.ArrayList;

public class SlideViewPagerAdapter extends PagerAdapter {
    Context ctx;
    ArrayList<String> mResources;

    public SlideViewPagerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object ;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater =(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_screen,container,false);

        ImageView imageView = view.findViewById(R.id.onboardingPicture);
TextView textView = view.findViewById(R.id.textView);
TextView titleText = view.findViewById(R.id.titleText);
        Button getStartedButton = view.findViewById(R.id.get_started_btn);
getStartedButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ctx, MainController.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK |intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);

    }
});

        switch (position){
            case 0 :
                imageView.setImageResource(R.drawable.page1);
                textView.setText(R.string.first_slide_desc);
                titleText.setText(R.string.first_slide_title);
                getStartedButton.setVisibility(View.GONE);
                break;
            case 1 :
                imageView.setImageResource(R.drawable.page2);
                textView.setText(R.string.second_slide_desc);
                titleText.setText(R.string.second_slide_title);
                getStartedButton.setVisibility(View.GONE);
                break;
            case 2:
                imageView.setImageResource(R.drawable.page3);
                textView.setText(R.string.third_slide_desc);
                titleText.setText(R.string.athird_slide_title);
                getStartedButton.setVisibility(View.VISIBLE);
        }
        container.addView(view);
        return view;
    }
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) (object));
    }
}
