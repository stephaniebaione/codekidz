package com.example.regina.ratapp;


import java.util.ArrayList;

public class RatReport {
    int uniqueKey;
    String createdData;
    String locationType; //gonna change to enum
    int incidentZip;
    String incidentAddress;
    String city; //might change to enum
    String borough; //gonna change to enum
    double latitude;
    double longitude;
    ArrayList<String> reportList;
    String data;

    public RatReport(int uniqueKey, String createdData, String locationType,
                     int incidentZip, String incidentAddress, String city,
                     String borough, double latitude, double longitude) {
        this.uniqueKey = uniqueKey;
        this.createdData = createdData;
        this.locationType = locationType;
        this.incidentZip = incidentZip;
        this.incidentAddress = incidentAddress;
        this.city = city;
        this.borough = borough;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getUniqueKey() {
        return uniqueKey;
    }

    public String uniqueKeyToString() { return String.valueOf(uniqueKey);}

    public void setUniqueKey(int uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getCreatedData() {
        return createdData;
    }

    public void setCreatedData(String createdData) {
        this.createdData = createdData;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public int getIncidentZip() {
        return incidentZip;
    }

    public String incidentZipToString() { return String.valueOf(incidentZip);}

    public void setIncidentZip(int incidentZip) {
        this.incidentZip = incidentZip;
    }

    public String getIncidentAddress() {
        return incidentAddress;
    }

    public void setIncidentAddress(String incidentAddress) {
        this.incidentAddress = incidentAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public double getLatitude() {
        return latitude;
    }

    public String latitudeToString() { return String.valueOf(latitude);}

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String longitudeToString() { return String.valueOf(longitude);}

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String createDataString(RatReport ratReport) {
        String thing = "Key: ";
        thing = thing + ratReport.uniqueKeyToString() + "\nCreated Data: "
                + ratReport.getCreatedData() + "\nLocation Type: " + ratReport.getLocationType() + "\nIncident Zip: "
                + ratReport.incidentZipToString() + "\nIncident Address: " + ratReport.getIncidentAddress()
                + "\nCity: " + ratReport.getCity() + "\nBorough: " + ratReport.getBorough() + "\nLatitude: "
                + ratReport.latitudeToString() + "\nLongitude: " + ratReport.longitudeToString();
        return thing;
    }

    public void addToReportList(RatReport ratReport) {
        reportList.add(createDataString(ratReport));
    }
}
