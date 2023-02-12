package com.example.myapplication.MainScreenTabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.DataSingelton;
import com.example.myapplication.MapsAPI.GoogleMapsAPI;
import com.example.myapplication.Spotify.ui.dashboard.DashboardFragment;

public class FirstPagerAdapter extends FragmentStateAdapter {
    public FirstPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new GoogleMapsAPI();
            case 1 :
                return new MLFragment();
            default:
                return new DashboardFragment();


        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
