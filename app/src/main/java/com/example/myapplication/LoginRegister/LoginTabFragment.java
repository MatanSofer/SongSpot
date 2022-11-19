package com.example.myapplication.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainScreenTabLayout.MainScreensActivity;
import com.example.myapplication.MapsAPI.GoogleMapsAPI;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {
    ViewGroup root;
    TextInputLayout passBorder,emailBorder;
    TextInputEditText pass,email;
    TextView forgetPass;
    Button login;
    float v =0;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        initView();

        login.setOnClickListener((View v) -> {
            login();

        });
        return root;
    }

    public void login(){

            String UserEmail = String.valueOf(email.getText());
            String UserPassword = String.valueOf(pass.getText());

            mAuth.signInWithEmailAndPassword(UserEmail, UserPassword).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                     passBorder.setError(null);
                    passBorder.setErrorEnabled(false);
                    emailBorder.setError(null);
                    emailBorder.setErrorEnabled(false);
                    Log.d("log","login  - user logged in succesfully");
                    Intent intent = new Intent(getActivity(), MainScreensActivity.class);
                    startActivity(intent);
                } else {
                    emailBorder.setError("Check your details again");
                    passBorder.setError("Check your details again");
                    Log.d("log","login  - user was not logged in succesfully"); }
            });

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