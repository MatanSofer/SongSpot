package com.example.myapplication.EditDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.FireBase.Model;
import com.example.myapplication.FireBase.ModelFireBase;
import com.example.myapplication.FireBase.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditDetailsActivity extends AppCompatActivity {

    ImageButton backButton;
    TextInputLayout emailBorder,passBorder,passVerificationBorder,ageBorder,genderBorder;
    TextInputEditText email,pass,passVerification,age;
    AutoCompleteTextView gender;
    Button saveDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener((View v)-> finish());


        initView();

        //dropdown menu adapter
        String[] lang = getResources().getStringArray(R.array.genders);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, lang);
        gender.setAdapter(arrayAdapter);


        saveDetails.setOnClickListener((View v) -> validateDetails());



    }
    public void initView(){
        emailBorder = findViewById(R.id.emailBorder);
        passBorder = findViewById(R.id.passBorder);
        passVerificationBorder = findViewById(R.id.conf_passBorder);
        ageBorder = findViewById(R.id.ageBorder);
        genderBorder = findViewById(R.id.genderBorder);


        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        passVerification = findViewById(R.id.conf_pass);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        saveDetails = findViewById(R.id.button);

        Model.instance.GetUserById(ModelFireBase.getCurrentUser(), (user) -> {
            email.setText(user.getEmail());
            age.setText(user.getAge());
            gender.setText(user.getGender());
        });

    }
    public void validateDetails(){
        if(!validateEmail() | !validatePassword() | !validateVerPassword() | !validateGender() | !validateAge()){
            return;

        }
        else{
            String email1 = String.valueOf(email.getText());
            String age1 = String.valueOf(age.getText());
            String gender1 = String.valueOf(gender.getText());
            String password1 = String.valueOf(pass.getText());
            User user = new User(email1, age1, gender1);
            Model.instance.addUser(user, () -> {
                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                user1.updatePassword(password1)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("log", "edit details - pass has been changed ");
                                    user1.updateEmail(email1)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("log", "edit details - email has been changed ");
                                                    }
                                                    else{
                                                        Log.d("log", "edit details - email changed failed");
                                                    }
                                                }
                                            });
                                }
                                else{
                                    Log.d("log", "edit details - pass changed failed");
                                }
                            }
                        });

                Log.d("log", "edit details - has been changed ");
            });
            finish();
        }

    }
    private Boolean validateEmail(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailStr = email.getText().toString();
        if(emailStr.isEmpty()){
            emailBorder.setError("Field cannot be empty");
            return false;
        }else if (!emailStr.matches(emailPattern)) {
            emailBorder.setError("Invalid email address");
            return false;
        }
        else{
            emailBorder.setError(null);
            emailBorder.setErrorEnabled(false);
            return true;
        }
    }

    String passwordVal = "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            //  "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 4 characters
            "$";
    private Boolean validatePassword(){
        String passwordStr = pass.getText().toString();
        if(passwordStr.isEmpty()){
            passBorder.setError("Field cannot be empty");
            return false;
        }else if(passwordStr.length() < 6 ) {
            passBorder.setError("Password too short");
            return false;
        }else if (!passwordStr.matches(passwordVal)) {
            passBorder.setError("Password is too weak");
            return false;
        }
        else{
            passBorder.setError(null);
            passBorder.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateVerPassword(){
        String passwordVerStr = passVerification.getText().toString();
        if(passwordVerStr.isEmpty()){
            passVerificationBorder.setError("Field cannot be empty");
            return false;
        }else if(!passwordVerStr.equals(pass.getText().toString())) {
            passVerificationBorder.setError("Passwords does not match");
            return false;
        }else if(passwordVerStr.length() < 6 ) {
            passVerificationBorder.setError("Password too short");
            return false;
        }else if (!passwordVerStr.matches(passwordVal)) {
            passVerificationBorder.setError("Password is too weak");
            return false;
        }
        else{
            passVerificationBorder.setError(null);
            passVerificationBorder.setErrorEnabled(false);
            return true;
        }
    }
    //8-99
    private Boolean validateAge(){
        String ageStr = age.getText().toString();
        if(ageStr.isEmpty()){
            ageBorder.setError("Field cannot be empty");
            return false;
        }else if(Integer.parseInt(ageStr) < 8 ){
            ageBorder.setError("Age must be above 7");
            return false;
        }
        else{
            ageBorder.setError(null);
            ageBorder.setErrorEnabled(false);
            return true;
        }
    }

    //male or female
    private Boolean validateGender(){
        String genderStr = gender.getText().toString();
        if(genderStr.isEmpty()){
            genderBorder.setError("Field cannot be empty");
            return false;
        }else if(!genderStr.equals("Male") && !genderStr.equals("Female")){
            genderBorder.setError("Gender must be from the list");
            return false;
        }
        else{
            genderBorder.setError(null);
            genderBorder.setErrorEnabled(false);
            return true;
        }
    }
}