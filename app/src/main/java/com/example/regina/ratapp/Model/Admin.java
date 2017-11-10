package com.example.regina.ratapp.Model;

import java.util.HashMap;

/**
 * Created by Regina on 9/28/2017.
 * This is a page for administrator that has special features
 */

public class Admin {
    private String email_address;
    private String password;
    private static final HashMap<String, String> adminList = new HashMap<>();

    /**
     * constructor for an admin user
     * @param email_address email of the admin
     * @param password password of the user
     */
    public Admin(String email_address, String password){
        this.email_address = email_address;
        this.password = password;
    }

    private String getEmail_address(){return email_address;}
    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    private String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public HashMap<String,String> getAdmins() {return adminList;}

    /**
     * tells if the admin is in the system or not
     * @param email_Address email to be checked
     * @return true if admin is in the system
     */
    public Boolean doesAccountExist(String email_Address) {

        return adminList.containsKey(email_Address.toLowerCase());
    }

    /*  adds user to list of users and adds their email to the account list... I think the account
    list might be able to be deleted
     */
    public void addNewAdmin(Admin newUser) {
        adminList.put(newUser.getEmail_address().toLowerCase(),
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
