package com.example.entities;

import java.util.ArrayList;
import java.util.List;

public class NowUser extends User {
    private static User instance;

    private NowUser(){

    }

    public static User getInstance(){
        if(instance == null){
            instance = new NowUser();
            return instance;
        }
        return instance;
    }

    public static void setInstance(User user){
        instance = user;
    }

}