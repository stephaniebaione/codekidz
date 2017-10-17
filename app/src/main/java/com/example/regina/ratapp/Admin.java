package com.example.regina.ratapp;

import java.util.HashMap;

/**
 * Created by Regina on 9/28/2017.
 */

public class Admin {
    String emailaddress;
    String password;
    static HashMap<String, String> adminList = new HashMap<>();

    /**
     * constructer for an admin user
     * @param emailaddress email of the admin
     * @param password password of the user
     */
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

    /**
     * tells if the admin is in the system or not
     * @param emailAddress email to be checked
     * @return true if admin is in the system
     */
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
    // Allows admin to block a user
    public void lockAccount(User user) {
        if (!(user.locked))
            user.setLocked(true);
    }
    // Allows admin to unlock a user
    public void unlockAccount(User user) {
        if (user.locked)
            user.setLocked(false);
    }
    // Checks whether an account is blocked or not
    public boolean isBanned(User user) {
        return user.getLocked();
    }

}
