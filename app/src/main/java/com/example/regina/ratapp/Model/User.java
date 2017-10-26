package com.example.regina.ratapp.Model;

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
    static ArrayList<User> userInformation = new ArrayList<>();
    static HashMap<String, Boolean> lockList = new HashMap<>();

    /**
     * Constructor for creating a user. Sets username and password, also title
     * @param emailaddress
     * @param password
     */
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

    public HashMap<String, Boolean> getLockList() {
        return lockList;
    }

    public userTitle getTitle() {
        return title;
    }
    public HashMap<String, String> getAccounts() {return accountList;}

    public void setTitle(userTitle title) {
        this.title = title;
    }

    public String toString(){
        String str = "Email:"+this.getEmailaddress() + "\n Reports Made:" + this.getNumberOfReports()+
                "\n Title:" + this.getTitle();
        return str;
    }

    /**
     * Looks at the account list to see if there is an email in the system
     * @param emailAddress
     * @return Boolean stating whether the account exists or not
     */
    public Boolean doesAccountExist(String emailAddress) {

        return accountList.containsKey(emailAddress.toLowerCase());
    }

    /**
     * Passes the new user in ands their information to the account list. It also checks whether the
     * username is associated with a locked account. If it is associated with a locked account it
     * will not create the new user
     * @param newUser
     */



    public void addNewUser(User newUser) {
        userInformation.add(newUser);
        accountList.put(newUser.getEmailaddress().toLowerCase(),
                newUser.getPassword());
        lockList.put(newUser.getEmailaddress().toLowerCase(),
                newUser.getLocked());

    }



}
