package com.example.myapplication.FireBase;

import android.util.Log;

import com.example.myapplication.DataSingelton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class ModelFireBase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseUser currentFirebaseUser;
    static FirebaseAuth mAuth;
    static String current;

    public static String getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        currentFirebaseUser = mAuth.getCurrentUser();
        if (currentFirebaseUser != null) {
            current = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseAuth.getInstance().getCurrentUser();
        } else {
            current = null;
        }

        return current;
    }

    public void GetUserById(String UserId, Model.GetUserByIdListener listener) {
        DocumentReference docRef = db.collection("user").document(UserId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("log", "ModelFireBase - get user by id is successful ", task.getException());
                    User u = User.fromJson(document.getData());
                    listener.onComplete(u);
                } else {
                    listener.onComplete(null);
                }
            } else {
                Log.d("log", "ModelFireBase - get user by id not successful ", task.getException());
                listener.onComplete(null);
            }
        });
    }
    public void addUser(User user1,Model.addUserListener listener){
        db.collection("user")
                .document(user1.getUserId()).set(user1.toJson())
                .addOnSuccessListener((successListener) -> {
                    Log.d("adduser","ModelFireBase - user add succesfully");
                    listener.onComplete();
                })
                .addOnFailureListener((e) -> {
                    Log.d("adduser", e.getMessage());
                    Log.d("adduser","ModelFireBase - user NOT added succesfully");

                });
    }




}

