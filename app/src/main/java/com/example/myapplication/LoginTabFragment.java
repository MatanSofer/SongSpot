package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginTabFragment extends Fragment {
    ViewGroup root;
    TextInputLayout passBorder,emailBorder;
    TextInputEditText pass,email;
    TextView forgetPass;
    Button login;
    float v =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        initView();


        return root;
    }

    public void initView(){
        passBorder = root.findViewById(R.id.passBorder);
        emailBorder = root.findViewById(R.id.emailBorder);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        forgetPass = root.findViewById(R.id.forget_pass);
        login = root.findViewById(R.id.button);


        emailBorder.setTranslationX(800);
        passBorder.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);


        emailBorder.setAlpha(v);
        passBorder.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);


        emailBorder.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        passBorder.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(700).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

    }
}