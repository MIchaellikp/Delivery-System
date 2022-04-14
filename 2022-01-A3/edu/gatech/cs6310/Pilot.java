package edu.gatech.cs6310;

import java.util.Date;

import static java.lang.CharSequence.compare;

public class Pilot implements Comparable<Pilot>{
    private String accountID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String taxID;
    private String licenseID;
    private int expcLevel;
    private Drone drone;
    private boolean flag;
    private Date timeStamp;


    /**
     * Helper method to initialize a pilot
     *
     */

    public Pilot(String accountID, String firstName, String lastName, String phoneNumber, String taxID, String licenseID, int expcLevel) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.taxID = taxID;
        this.licenseID = licenseID;
        this.expcLevel = expcLevel;
        this.drone = null;
        this.flag = false;
        this.timeStamp = new Date();
    }

    /**
     * Helper method to add pilot'e experience
     *
     * @param exp the content of new experience
     */

    public void addExpcLevel(int exp){
        this.expcLevel += exp;
    }

    @Override
    public String toString() {
        return "name:" + firstName + "_" + lastName + ",phone:" + phoneNumber +
                ",taxID:" + taxID + ",licenseID:" + licenseID + ",experience:" + expcLevel;
    }

    public static int compareStrings(String s1, String s2) {
        return compare(s1,s2);
    }

    @Override
    public int compareTo(Pilot pilot) {
        return compareStrings(this.accountID, pilot.getAccountID());
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getLicenseID() {
        return licenseID;
    }

    public void setLicenseID(String licenseID) {
        this.licenseID = licenseID;
    }

    public int getExpcLevel() {
        return expcLevel;
    }

    public void setExpcLevel(int expcLevel) {
        this.expcLevel = expcLevel;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
