package com.example.regina.ratapp.Model;


import java.util.ArrayList;

public class RatReport {
    int uniqueKey;
    String createdData;
    String locationType; //gonna change to enum
    String incidentZip;
    String incidentAddress;
    String city; //might change to enum
    String borough; //gonna change to enum
    double latitude;
    double longitude;
    static ArrayList<String> reportList;
    String data;

    /**
     * constructor to create rat report
     * @param uniqueKey unique key
     * @param createdData date created
     * @param locationType what kind of location the rat was spotted
     * @param incidentZip zip code of incident
     * @param incidentAddress address of incident
     * @param city city of incident
     * @param borough neighborhoodo f incident
     * @param latitude latitude of incident
     * @param longitude longitude of incident
     */
    public RatReport(int uniqueKey, String createdData, String locationType,
                     String incidentZip, String incidentAddress, String city,
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
    public ArrayList<String> getReportList(){return reportList;}

    /**
     * turns unique key to string
     * @return string of key
     */
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

    public String getIncidentZip() {
        return incidentZip;
    }

    /**
     * turns zip code to a string
     * @return string of zip code
     */
    public String incidentZipToString() { return String.valueOf(incidentZip);}

    public void setIncidentZip(String incidentZip) {
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
    /**
     * turns latitude to a string
     * @return string of latitude
     */
    public String latitudeToString() { return String.valueOf(latitude);}

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    /**
     * turns longitude to a string
     * @return string of longitude
     */
    public String longitudeToString() { return String.valueOf(longitude);}

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * creates a string that list all the data of the report with a format
     * @param ratReport report to be formatted
     * @return string of formatted data
     */
    public String createDataString(RatReport ratReport) {
        String thing = "Key: ";
        thing = thing + ratReport.uniqueKeyToString() + "\nCreated Data: "
                + ratReport.getCreatedData() + "\nLocation Type: " + ratReport.getLocationType() + "\nIncident Zip: "
                + ratReport.incidentZipToString() + "\nIncident Address: " + ratReport.getIncidentAddress()
                + "\nCity: " + ratReport.getCity() + "\nBorough: " + ratReport.getBorough() + "\nLatitude: "
                + ratReport.latitudeToString() + "\nLongitude: " + ratReport.longitudeToString();
        return thing;
    }

    /**
     * adds report to a list for tracking
     * @param ratReport report to be added
     */
    public void addToReportList(RatReport ratReport) {
        reportList.add(createDataString(ratReport));
    }
}
