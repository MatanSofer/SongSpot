package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OnBoardingFragment1 extends Fragment {
    @Nullable
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_on_boarding1,container,false);
        return root;

       // view = super.onCreateView(inflater, container, savedInstanceState);
       // return view;
    }
}
