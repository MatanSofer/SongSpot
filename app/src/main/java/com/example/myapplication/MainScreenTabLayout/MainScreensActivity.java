package com.example.myapplication.MainScreenTabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.DataSingelton;
import com.example.myapplication.EditDetails.EditDetailsActivity;
import com.example.myapplication.FireBase.Model;
import com.example.myapplication.FireBase.ModelFireBase;
import com.example.myapplication.LunchScreens.IntroductryActivity;
import com.example.myapplication.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class MainScreensActivity extends AppCompatActivity {

    ImageButton editDetailsButton , exitButton;
    public static TabLayout tabLayout;
    private FirebaseAuth mAuth;
    public static ViewPager2 viewPager2;

    public static boolean enableSwipe = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R
                .layout.activity_main_screens2);


        editDetailsButton = findViewById(R.id.profile_icon);
        exitButton = findViewById(R.id.exit_icon);

        editDetailsButton.setOnClickListener((View v)-> {
            Intent editDetailsActivity = new Intent(this, EditDetailsActivity.class);
            startActivity(editDetailsActivity);
        });
        exitButton.setOnClickListener((View v)->{
            logout();
        });
        Model.instance.GetUserById(ModelFireBase.getCurrentUser(), (user) -> {
            Integer age = Integer.parseInt(user.getAge());
            DataSingelton.setAge(age);
            String gender = user.getGender().toLowerCase(Locale.ROOT);
            DataSingelton.setGender(gender);
        });
        viewPager2 =findViewById(R.id.view_pager);
        viewPager2.setAdapter(new FirstPagerAdapter(this));
        viewPager2.setUserInputEnabled(false);

        tabLayout = findViewById(R.id.tabLayout);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                 tab.view.setClickable(false);
                switch (position) {
                    case 0: {
                        tab.setText("Location");
                        tab.setIcon(R.drawable.first_tab_icon);
                        break;
                    }
                    case 1: {
                        tab.setText("Playlist");
                        tab.setIcon(R.drawable.third_tab_icon);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(ContextCompat
                                .getColor(getApplicationContext(), R.color.color6));
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(0);
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();

        // Add custom click listener on the TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(enableSwipe) {
                    viewPager2.setAdapter(new FirstPagerAdapter(MainScreensActivity.this));
                    viewPager2.setCurrentItem(tab.getPosition(), false);
                    enableSwipe = false;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void logout() {

        FirebaseAuth.getInstance().signOut();
        Intent introductryActivity = new Intent(this, IntroductryActivity.class);
        startActivity(introductryActivity);
    }
}