package com.example.myapplication.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.FireBase.Model;
import com.example.myapplication.FireBase.User;
import com.example.myapplication.MainScreenTabLayout.MainScreensActivity;
import com.example.myapplication.R;
import com.example.myapplication.Spotify.SpotifyStartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupTabFragment extends Fragment {
    ViewGroup root;
    TextInputLayout emailBorder, passBorder, passVerificationBorder, ageBorder, genderBorder;
    TextInputEditText email, pass, passVerification, age;
    AutoCompleteTextView gender;
    Button login;
    float v = 0;
    private FirebaseAuth mAuth;
    ProgressBar progressBarLoading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        initView();

        //dropdown menu adapter
        String[] lang = getResources().getStringArray(R.array.genders);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, lang);
        gender.setAdapter(arrayAdapter);

        login.setOnClickListener((View v) -> registerUser());

        return root;
    }

    public void initView() {
        emailBorder = root.findViewById(R.id.emailBorder);
        passBorder = root.findViewById(R.id.passBorder);
        passVerificationBorder = root.findViewById(R.id.conf_passBorder);
        ageBorder = root.findViewById(R.id.ageBorder);
        genderBorder = root.findViewById(R.id.genderBorder);

        progressBarLoading = root.findViewById(R.id.progressBar);
        progressBarLoading.setVisibility(View.INVISIBLE);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        passVerification = root.findViewById(R.id.conf_pass);
        age = root.findViewById(R.id.age);
        gender = root.findViewById(R.id.gender);
        login = root.findViewById(R.id.button);

        emailBorder.setTranslationX(800);
        passBorder.setTranslationX(800);
        passVerificationBorder.setTranslationX(800);
        ageBorder.setTranslationX(800);
        genderBorder.setTranslationX(800);
        login.setTranslationX(800);

        emailBorder.setAlpha(v);
        passBorder.setAlpha(v);
        passVerificationBorder.setAlpha(v);
        ageBorder.setAlpha(v);
        genderBorder.setAlpha(v);
        login.setAlpha(v);

        emailBorder.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        passBorder.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        passVerificationBorder.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        ageBorder.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        genderBorder.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();

    }

    public void registerUser() {
        Log.d("log", "signup activity - regising user");

        if (!validateEmail() | !validatePassword() | !validateVerPassword() | !validateGender() | !validateAge()) {
            return;
        } else {
            progressBarLoading.setVisibility(View.VISIBLE);
            String email1 = String.valueOf(email.getText());
            String age1 = String.valueOf(age.getText());
            String gender1 = String.valueOf(gender.getText());
            String password1 = String.valueOf(pass.getText());

            mAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        User user = new User(email1, age1, gender1);
                        Model.instance.addUser(user, () -> {
                            Log.d("log", "signup activity - has been added");
                            progressBarLoading.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(getActivity(), SpotifyStartActivity.class);
                            startActivity(intent);
                        });
                    } else {
                        Log.d("log", "signup activity -  user has not been added" + task.getException());
                    }
                }
            });
        }

    }

    private Boolean validateEmail() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailStr = email.getText().toString();
        if (emailStr.isEmpty()) {
            emailBorder.setError("Field cannot be empty");
            return false;
        } else if (!emailStr.matches(emailPattern)) {
            emailBorder.setError("Invalid email address");
            return false;
        } else {
            emailBorder.setError(null);
            emailBorder.setErrorEnabled(false);
            return true;
        }
    }

    String passwordVal = "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 4 characters
            "$";

    private Boolean validatePassword() {
        String passwordStr = pass.getText().toString();
        if (passwordStr.isEmpty()) {
            passBorder.setError("Field cannot be empty");
            return false;
        } else if (passwordStr.length() < 6) {
            passBorder.setError("Password too short");
            return false;
        } else if (!passwordStr.matches(passwordVal)) {
            passBorder.setError("Password is too weak");
            return false;
        } else {
            passBorder.setError(null);
            passBorder.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateVerPassword() {
        String passwordVerStr = passVerification.getText().toString();
        if (passwordVerStr.isEmpty()) {
            passVerificationBorder.setError("Field cannot be empty");
            return false;
        } else if (!passwordVerStr.equals(pass.getText().toString())) {
            passVerificationBorder.setError("Passwords does not match");
            return false;
        } else if (passwordVerStr.length() < 6) {
            passVerificationBorder.setError("Password too short");
            return false;
        } else if (!passwordVerStr.matches(passwordVal)) {
            passVerificationBorder.setError("Password is too weak");
            return false;
        } else {
            passVerificationBorder.setError(null);
            passVerificationBorder.setErrorEnabled(false);
            return true;
        }
    }

    //8-99
    private Boolean validateAge() {
        String ageStr = age.getText().toString();
        if (ageStr.isEmpty()) {
            ageBorder.setError("Field cannot be empty");
            return false;
        } else if (Integer.parseInt(ageStr) < 8) {
            ageBorder.setError("Age must be above 7");
            return false;
        } else {
            ageBorder.setError(null);
            ageBorder.setErrorEnabled(false);
            return true;
        }
    }

    //male or female
    private Boolean validateGender() {
        String genderStr = gender.getText().toString();
        if (genderStr.isEmpty()) {
            genderBorder.setError("Field cannot be empty");
            return false;
        } else if (!genderStr.equals("Male") && !genderStr.equals("Female")) {
            genderBorder.setError("Gender must be from the list");
            return false;
        } else {
            genderBorder.setError(null);
            genderBorder.setErrorEnabled(false);
            return true;
        }
    }
}