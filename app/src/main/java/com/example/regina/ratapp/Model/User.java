package com.example.regina.ratapp.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Abby on 9/27/2017.
 * User class that represents a user in the system.
 * It has email, password, and user titles, and can
 * be locked and unlocked.
 */

public class User {
    private String emailaddress;
    private String password;
    Boolean locked;
    private int numberOfReports;
    private userTitle title;
    private static final HashMap<String, String> accountList = new HashMap<>();
    private static final ArrayList<User> userInformation = new ArrayList<>();
    private static final HashMap<String, Boolean> lockList = new HashMap<>();

    /**
     * Constructor for creating a user. Sets username and password, also title
     * @param emailaddress user's email address
     * @param password user's password
     */
    public User(String emailaddress, String password) {
        this.emailaddress = emailaddress;
        this.password = password;
        locked = false;
        title = userTitle.MOUSE;
        numberOfReports = 0;
    }

    private String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    private String getPassword() {
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

    private int getNumberOfReports() {
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

    private void setTitle(userTitle title) {
        this.title = title;
    }

    public ArrayList<User> getUserInformation() {
        return userInformation;
    }

    public void updateUserTitle() {
        if (this.getNumberOfReports() > 40){
            this.setTitle(userTitle.PIEDPIPER);
        }else if (this.getNumberOfReports() > 30){
            this.setTitle(userTitle.SNITCH);
        }else if (this.getNumberOfReports() > 20){
            this.setTitle(userTitle.DIRTYRAT);
        }else if (this.getNumberOfReports() > 10){
            this.setTitle(userTitle.RAT);
        }
    }

    public String toString(){
        return "Email:"+ this.getEmailaddress() + "\n Reports Made:" +
                this.getNumberOfReports()+ "\n Title:" + this.getTitle();
    }

    /**
     * Looks at the account list to see if there is an email in the system
     * @param emailAddress user's email address
     * @return Boolean stating whether the account exists or not
     */
    public Boolean doesAccountExist(String emailAddress) {

        return accountList.containsKey(emailAddress.toLowerCase());
    }

    /**
     * Passes the new user in ands their information to the account list. It also checks whether the
     * username is associated with a locked account. If it is associated with a locked account it
     * will not create the new user
     * @param newUser new user to be added
     */



    public void addNewUser(User newUser) {
        userInformation.add(newUser);
        accountList.put(newUser.getEmailaddress().toLowerCase(),
                newUser.getPassword());
        lockList.put(newUser.getEmailaddress().toLowerCase(),
                newUser.getLocked());

    }



}
