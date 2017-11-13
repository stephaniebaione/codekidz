package com.example.regina.ratapp;

import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

import com.example.regina.ratapp.Controller.GraphActivity;
import com.example.regina.ratapp.Model.GraphQueryManager;

/**
 * Created by Evan Brook on 11/10/2017.
 * Used to test the SameYearUnit method in the GraphQueryManager
 */

public class SameYearUnitTest {
    @Test
    public void sameExactYearTest() {
        GraphActivity activity = new GraphActivity();
        GraphQueryManager manager = new GraphQueryManager(activity);
        int firstYear = 1990;
        int lastYear = 1990;
        assertTrue(manager.sameYear(firstYear, lastYear));
    }
    @Test
    public void notExactYearTest() {
        GraphActivity activity = new GraphActivity();
        GraphQueryManager manager = new GraphQueryManager(activity);
        int firstYear = 1990;
        int lastYear = 1991;
        assertFalse(manager.sameYear(firstYear, lastYear));
    }
}
