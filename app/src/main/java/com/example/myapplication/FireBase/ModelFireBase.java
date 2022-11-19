package com.example.myapplication.FireBase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ModelFireBase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseUser currentFirebaseUser;
    static FirebaseAuth mAuth;
    static User globalUser;
    static String current;

    public static String getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        currentFirebaseUser = mAuth.getCurrentUser();
        if (currentFirebaseUser != null) {
            current = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            current = null;
        }

        return current;
    }



    public void getAllUsers(Model.getAllUsersListener listener) {
        List<User> data=new LinkedList<User>();

        try {
            listener.onComplete(data);
        } catch (Exception e) {
        }
    }

    public void GetUserById(String UserId, Model.GetUserByIdListener listener) {
        DocumentReference docRef = db.collection("user").document(UserId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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
            }
        });
    }






    public void addUser(User user1,Model.addUserListener listener){
        db.collection("user")
                .document(user1.getUserId()).set(user1.toJson())
                .addOnSuccessListener((successListener) -> {
                    Log.d("log","ModelFireBase - user add succesfully");
                    listener.onComplete();
                })
                .addOnFailureListener((e) -> {
                    Log.d("log", e.getMessage());
                    Log.d("log","ModelFireBase - user NOT added succesfully");

                });

    }

    public void updateUser(User user1,Model.updateUserListener  listener){


    }


}
