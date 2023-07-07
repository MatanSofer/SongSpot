package com.example.myapplication.FireBase;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class User {

    String email;
    String age;
    String gender;
    String UserId ;
    public User(){}

    public User(String email, String age, String gender) {
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    public String getUserId() {
        return UserId;
    }
    public void setUserId(String userId) {
        UserId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Map<String, Object> toJson() {
        // Create a new user with different fields
        Map<String, Object> json = new HashMap<>();
        json.put("email", getEmail());
        json.put("age", getAge());
        json.put("gender", getGender());
        return json;
    }

    static User fromJson(Map<String, Object> json) {
        String email1 = (String) json.get("email");
        String age1 = (String) json.get("age");
        String gender1 = (String) json.get("gender");
        User user = new User(email1,age1, gender1);
        return user;

    }
}
