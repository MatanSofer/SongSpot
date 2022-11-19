package com.example.myapplication.FireBase;

import java.util.List;

public class Model {
    public final static Model instance = new Model();
    ModelFireBase modelFireBase = new ModelFireBase();

    private Model(){

    }

    public interface getAllUsersListener{
        void onComplete(List<User> users);
    }
    public void getAllStudents(getAllUsersListener listener){
        modelFireBase.getAllUsers(listener);
    }
    public interface GetUserByIdListener {
        void onComplete(User user);
    }

    public void GetUserById(String userID, GetUserByIdListener listener) {
        modelFireBase.GetUserById(userID, listener);
    }

    public interface addUserListener{
        void onComplete();
    }
    public void addUser(User user ,addUserListener listener){
        modelFireBase.addUser(user,listener);
    }
    public interface updateUserListener{
        void onComplete();
    }
    public void updateUser(User user ,updateUserListener listener){
        modelFireBase.updateUser(user,listener);
    }









}
