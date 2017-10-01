package com.example.regina.ratapp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Abby on 9/27/2017.
 */

public class User {
    String emailaddress;
    String password;
    Boolean locked;
    int numberOfReports;
    userTitle title;
    static HashMap<String, String> accountList = new HashMap<>();
    ArrayList<User> userInformation = new ArrayList<>();

    public User(String emailaddress, String password) {
        this.emailaddress = emailaddress;
        this.password = password;
        locked = false;
        title = userTitle.MOUSE;
        numberOfReports = 0;
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
    public HashMap<String, String> getAccounts() {return accountList;}

    public void setTitle(userTitle title) {
        this.title = title;
    }
    // Checks if the email is already in the system or not
    public Boolean doesAccountExist(String emailAddress) {

        return accountList.containsKey(emailAddress.toLowerCase());
    }

    /*  adds user to list of users and adds their email to the account list... I think the account
    list might be able to be deleted
     */
    public void addNewUser(User newUser) {
        userInformation.add(newUser);
        accountList.put(newUser.getEmailaddress().toLowerCase(),
                newUser.getPassword());

    }



}
