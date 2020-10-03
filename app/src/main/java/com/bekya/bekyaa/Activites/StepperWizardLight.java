package com.bekya.bekyaa.Activites;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bekya.bekyaa.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class StepperWizardLight extends AppCompatActivity {

    private static final int MAX_STEP = 6;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private Button btnNext;

    private int about_images_array[] = {
            R.drawable.screen_1,
            R.drawable.screen_2,
            R.drawable.screen_3,
            R.drawable.screen_4,
            R.drawable.screen_5,
            R.drawable.screen_6,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper_wizard_light);

        initComponent();
    }

    private void initComponent() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        btnNext = (Button) findViewById(R.id.btn_next);

        // adding bottom dots
        bottomProgressDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < MAX_STEP) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    finish();
                }
            }
        });



    }

    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.orange_400), PorterDuff.Mode.SRC_IN);
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);

            if (position == about_images_array.length - 1) {
                btnNext.setText(getString(R.string.GOT_IT));
                btnNext.setBackgroundColor(getResources().getColor(R.color.orange_400));
                btnNext.setTextColor(Color.WHITE);

            } else {
                btnNext.setText(getString(R.string.NEXT));
                btnNext.setBackgroundColor(getResources().getColor(R.color.grey_10));
                btnNext.setTextColor(getResources().getColor(R.color.grey_90));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_stepper_wizard, container, false);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return about_images_array.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}