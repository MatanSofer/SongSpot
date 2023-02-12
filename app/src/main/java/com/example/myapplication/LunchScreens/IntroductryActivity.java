package com.example.myapplication.LunchScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.FireBase.ModelFireBase;
import com.example.myapplication.MainScreenTabLayout.MainScreensActivity;
import com.example.myapplication.R;
import com.example.myapplication.Spotify.SpotifyStartActivity;

public class IntroductryActivity extends AppCompatActivity {
    ImageView logo,splashImg;
    LottieAnimationView lottieAnimationView;
    private static final int NUM_PAGE = 3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductry);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        logo = findViewById(R.id.logo);
        splashImg = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        anim = AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.startAnimation(anim);

        splashImg.animate().translationY(-2600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2400).setDuration(1000).setStartDelay(4000);
        checkUserInstance();

    }
    public void checkUserInstance(){
        if(ModelFireBase.getCurrentUser()!=null){
            Log.d("log","IntroductryAtivity- user ID is "+ModelFireBase.getCurrentUser());
        }else{
            Log.d("log","IntroductryAtivity- user ID not found");
        }
        if (ModelFireBase.getCurrentUser() != null) {
            Log.d("log","IntroductryAtivity- instance is exist");
            //Intent intent = new Intent(this, MainScreensActivity.class);
            Intent intent = new Intent(this, SpotifyStartActivity.class);
            startActivity(intent);

        } else {
            Log.d("log","IntroductryAtivity- instance isnt exist");
        }

    }
    private static class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                    return tab1;
                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;

            }
            return null;

        }

        @Override
        public int getCount() {
            return NUM_PAGE;
        }
    }
}