package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {

    EditText email,pass,passVerification,mobile;
    Button login;
    float v =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        passVerification = root.findViewById(R.id.conf_pass);
        mobile = root.findViewById(R.id.mobile_num);
        login = root.findViewById(R.id.button);

        email.setTranslationX(800);
        pass.setTranslationX(800);
        passVerification.setTranslationX(800);
        mobile.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        passVerification.setAlpha(v);
        mobile.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        passVerification.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        mobile.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();



        return root;
    }
}