package com.example.regina.ratapp;

import com.example.regina.ratapp.Model.User;
import com.example.regina.ratapp.Model.userTitle;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Regina on 11/9/2017.
 * Junit test class to test the user title update function.
 */

public class UpdateUserUnitTest {
    User newbie = new User("email", "pass");
    @Test
    public void noUpdate(){
        newbie.updateUserTitle();
        assertEquals(userTitle.MOUSE, newbie.getTitle());
    }
    @Test
    public void beforeUpdate(){
        newbie.setNumberOfReports(10);
        newbie.updateUserTitle();
        assertEquals(userTitle.MOUSE, newbie.getTitle());
    }
    @Test
    public void firstUpdate(){
        newbie.setNumberOfReports(11);
        newbie.updateUserTitle();
        assertEquals(userTitle.RAT, newbie.getTitle());
    }
    @Test
    public void beforeSecondUpdate(){
        newbie.setNumberOfReports(20);
        newbie.updateUserTitle();
        assertEquals(userTitle.RAT, newbie.getTitle());
    }

    @Test
    public void secondUpdate(){
        newbie.setNumberOfReports(21);
        newbie.updateUserTitle();
        assertEquals(userTitle.DIRTYRAT, newbie.getTitle());
    }
    @Test
    public void beforeThirdUpdate(){
        newbie.setNumberOfReports(30);
        newbie.updateUserTitle();
        assertEquals(userTitle.DIRTYRAT, newbie.getTitle());
    }
    @Test
    public void thirdUpdate(){
        newbie.setNumberOfReports(31);
        newbie.updateUserTitle();
        assertEquals(userTitle.SNITCH, newbie.getTitle());
    }
    @Test
    public void beforefourthUpdate(){
        newbie.setNumberOfReports(40);
        newbie.updateUserTitle();
        assertEquals(userTitle.SNITCH, newbie.getTitle());
    }
    @Test
    public void fourthUpdate(){
        newbie.setNumberOfReports(41);
        newbie.updateUserTitle();
        assertEquals(userTitle.PIEDPIPER, newbie.getTitle());
    }

}
