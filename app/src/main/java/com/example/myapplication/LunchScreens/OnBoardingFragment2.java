package com.example.myapplication.LunchScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginRegister.LoginActivity;
import com.example.myapplication.R;

public class OnBoardingFragment2 extends Fragment {
    TextView SkipTv;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_on_boarding2,container,false);
        SkipTv = root.findViewById(R.id.SkipTV);
        SkipTv.setOnClickListener((View v) -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
        return root;

        // view = super.onCreateView(inflater, container, savedInstanceState);
        // return view;
    }
}
