package com.example.myapplication.MainScreenTabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainScreensActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R
                .layout.activity_main_screens2);

        ViewPager2 viewPager2 =findViewById(R.id.view_pager);
        viewPager2.setAdapter(new FirstPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:{
                        tab.setText("Location");
                        tab.setIcon(R.drawable.first_tab_icon);
                        break;
                    }
                    case 1:{
                        tab.setText("Preference");
                        tab.setIcon(R.drawable.second_tab_icon);
                        break;
                    }
                    case 2:{
                        tab.setText("Playlist");
                        tab.setIcon(R.drawable.third_tab_icon);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(ContextCompat
                                .getColor(getApplicationContext(),R.color.color6));
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(0);
                        break;
                    }



                }
            }
        }
        );
        tabLayoutMediator.attach();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                BadgeDrawable badgeDrawable = tabLayout.getTabAt(position).
//                        getOrCreateBadge();
//                badgeDrawable.setVisible(true);
            }

    });
    }
}