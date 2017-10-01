package com.example.regina.ratapp;

import java.util.HashMap;

/**
 * Created by Regina on 9/28/2017.
 */

public class Admin {
    String emailaddress;
    String password;
    static HashMap<String, String> adminList = new HashMap<>();

    public Admin(String emailaddress, String password){
        this.emailaddress = emailaddress;
        this.password = password;
    }

    public String getEmailaddress(){return emailaddress;}
    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public HashMap<String,String> getAdmins() {return adminList;}

    public Boolean doesAccountExist(String emailAddress) {

        return adminList.containsKey(emailAddress.toLowerCase());
    }

    /*  adds user to list of users and adds their email to the account list... I think the account
    list might be able to be deleted
     */
    public void addNewAdmin(Admin newUser) {
        adminList.put(newUser.getEmailaddress().toLowerCase(),
                newUser.getPassword());

    }

}
