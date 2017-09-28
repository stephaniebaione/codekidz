package com.example.regina.ratapp;

/**
 * Created by Abby on 9/27/2017.
 */

public class User {
    String emailaddress;
    String password;
    Boolean locked;
    int numberOfReports;
    userTitle title;

    public User(String emailaddress, String password) {
        this.emailaddress = emailaddress;
        this.password = password;
        locked = false;
        title = userTitle.MOUSE;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    public userTitle getTitle() {
        return title;
    }

    public void setTitle(userTitle title) {
        this.title = title;
    }
}
