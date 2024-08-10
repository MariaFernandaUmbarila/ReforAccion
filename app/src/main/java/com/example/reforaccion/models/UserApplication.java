package com.example.reforaccion.models;

import android.app.Application;

import java.util.Date;

public class UserApplication extends Application {

    public User user;

    public UserApplication(User user) {
        this.user = user;
    }
    public UserApplication() {
        this.user = new User( "","","",new Date() ,false);
    }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

}
