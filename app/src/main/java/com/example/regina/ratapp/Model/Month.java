package com.example.regina.ratapp.Model;

/**
 * Created by mrobjectionman on 10/28/2017.
 */

public enum Month {
    JANUARY ("01"),
    FEBRUARY ("02"),
    MARCH ("03"),
    APRIL ("04"),
    MAY ("05"),
    JUNE ("06"),
    JULY ("07"),
    AUGUST ("08"),
    SEPTEMBER ("09"),
    OCTOBER ("10"),
    NOVEMBER ("11"),
    DECEMBER ("12");

    private final String monthCode;
    Month(String code) {
        this.monthCode = code;
    }
    public String getMonthCode() {
        return this.monthCode;
    }
}
