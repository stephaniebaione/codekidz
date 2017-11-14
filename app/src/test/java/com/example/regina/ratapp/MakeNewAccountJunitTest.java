package com.example.regina.ratapp;

import com.example.regina.ratapp.Controller.RegisterActivity;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

/**
 * Created by Stephanie Baione on 11/8/2017.
 */

public class MakeNewAccountJunitTest {
    @Test
    public void existingUserTest() {
        RegisterActivity ra = new RegisterActivity();
        String email = "stephaniebaione@gatech.edu";
        String password = "password";
        String type = "User";

        assertTrue(ra.makeNewAccount(email, password, type));
    }

    @Test
    public void NewUserTest() {
        RegisterActivity ra = new RegisterActivity();
        String email = "thereIsNoWayThatSomeoneHasThisEmail@gatech.edu";
        String password = "password";
        String type = "User";

        assertFalse(ra.makeNewAccount(email, password, type));
    }
}
