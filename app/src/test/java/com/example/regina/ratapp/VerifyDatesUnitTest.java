package com.example.regina.ratapp;

/**
 * Created by Abby on 11/12/2017.
 * Tests the ValidDates Method in Query Manager
 */

import com.example.regina.ratapp.Controller.MapsActivity;
import com.example.regina.ratapp.Model.QueryManager;

import org.junit.Test;


import static org.junit.Assert.*;
public class VerifyDatesUnitTest {
   // tests if the First Year is Greater than The Second year, months are the same
    @Test
    public void FirstYearGreaterThanSecondTest() throws Exception {
        int firstMonth = 1;
        int secondMonth = 1;
        int firstYear = 2015;
        int secondYear = 2013;
        MapsActivity testActivity = new MapsActivity();
        QueryManager queryManager = new QueryManager(testActivity);
        assertFalse(queryManager.validDates(firstMonth,secondMonth,firstYear,secondYear));

    }
    //Tests if second year is greater than the first
    @Test
    public void SecondYearGreaterThanFirstTest() throws Exception {
        int firstMonth = 1;
        int secondMonth = 1;
        int firstYear = 2015;
        int secondYear = 2016;
        MapsActivity testActivity = new MapsActivity();
        QueryManager queryManager = new QueryManager(testActivity);
        assertTrue(queryManager.validDates(firstMonth,secondMonth,firstYear,secondYear));

    }
    // tests if years are the same but the first month comes before the second
    @Test
    public void FirstMonthGreaterThanSecondTest() throws Exception {
        int firstMonth = 5;
        int secondMonth = 1;
        int firstYear = 2015;
        int secondYear = 2015;
        MapsActivity testActivity = new MapsActivity();
        QueryManager queryManager = new QueryManager(testActivity);
        assertFalse(queryManager.validDates(firstMonth,secondMonth,firstYear,secondYear));

    }
    // Tests the case where the years entered are the same and the months are in chronological order
   @Test
    public void FirstMonthBeforeTheSecondMonthTest() throws Exception {
        int firstMonth = 1;
        int secondMonth = 3;
        int firstYear = 2015;
        int secondYear = 2015;
        MapsActivity testActivity = new MapsActivity();
        QueryManager queryManager = new QueryManager(testActivity);
        assertTrue(queryManager.validDates(firstMonth,secondMonth,firstYear,secondYear));

    }
    // Tests the case where the years entered are the same and the months are also the same
    @Test
    public void EqualMonthsAndYears() throws Exception {
        int firstMonth = 1;
        int secondMonth = 1;
        int firstYear = 2015;
        int secondYear = 2015;
        MapsActivity testActivity = new MapsActivity();
        QueryManager queryManager = new QueryManager(testActivity);
        assertTrue(queryManager.validDates(firstMonth,secondMonth,firstYear,secondYear));

    }
}
