package com.example.regina.ratapp;

import com.example.regina.ratapp.Model.Admin;
import com.example.regina.ratapp.Model.User;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

/**
 * Created by Paige on 11/9/2017.
 */

public class LockUnlockAccountUnitTest {
    Admin admin = new Admin("email","password");
    User user = new User("email","password");
    @Test
    public void userNotLocked() {
        admin.lockAccount(user);
        assertTrue(user.getLocked());
    }
    @Test
    public void userLocked() {
        admin.unlockAccount(user);
        assertFalse(user.getLocked());
    }
    @Test
    public void userAlreadyLocked () {
        admin.lockAccount(user);
        assertTrue(user.getLocked());
    }
    @Test
    public void userAlreadyUnlocked() {
        admin.unlockAccount(user);
        assertFalse(user.getLocked());
    }

}
