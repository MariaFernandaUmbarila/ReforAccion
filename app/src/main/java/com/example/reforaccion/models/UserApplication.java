package com.example.reforaccion.models;

import android.app.Application;
import java.util.ArrayList;
import java.util.Date;

public class UserApplication extends Application {

    public User user;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public UserApplication(User user) {
        this.user = user;
    }
    public UserApplication() {
        this.user = new User( "","","",new Date() ,false, new ArrayList<Plant>(), new ArrayList<Area>());
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

}
