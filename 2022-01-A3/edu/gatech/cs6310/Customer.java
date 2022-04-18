package edu.gatech.cs6310;

import java.util.Date;

import static java.lang.CharSequence.compare;

public class Customer implements Comparable<Customer>{
    private String customerID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int rating;
    private int credits;
    private int remainingCredits;
    private boolean flag;
    private Date timeStamp;

    /**
     * Helper method to initialize a Customer
     *
     */

    public Customer(String account, String firstName, String lastName, String phoneNumber, int rating, int credits) {
        this.customerID = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.credits = credits;
        this.remainingCredits = credits;
        this.flag = false;
        this.timeStamp = new Date();
    }

    public String toString(AppSettings settings){
        return "name:" + firstName + "_" + lastName + ",phone:" + phoneNumber +
                ",rating:" + rating + ",credit:" + credits * settings.getCurrencyMultiplier() + settings.getDisplayCurrency() ;
    }

    public String toString_withArchiveState(AppSettings settings){
        if (this.isFlag())
            return "name:" + firstName + "_" + lastName + ",phone:" + phoneNumber +
                    ",rating:" + rating + ",credit:" + credits * settings.getCurrencyMultiplier() + settings.getDisplayCurrency()  + " (Archived)";
        else
            return "name:" + firstName + "_" + lastName + ",phone:" + phoneNumber +
                    ",rating:" + rating + ",credit:" + credits * settings.getCurrencyMultiplier()  +settings.getDisplayCurrency() + " (Active)";
    }

    public static int compareStrings(String s1, String s2) {
        return compare(s1,s2);
    }

    @Override
    public int compareTo(Customer customer) {
        return compareStrings(this.customerID, customer.getAccount());
    }

    public void changeRemainingCredits(int requestTotalCost){
        this.remainingCredits -= requestTotalCost;
    }

    public void purchase(int totalCost){
        this.credits -= totalCost;
    }

    public String getAccount() {
        return customerID;
    }

    public void setAccount(String account) {
        this.customerID = account;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getRemainingCredits() {
        return remainingCredits;
    }

    public void setRemainingCredits(int remainingCredits) {
        this.remainingCredits = remainingCredits;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
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

    public void setTimeStamp(Date dataStamp) {
        this.timeStamp = dataStamp;
    }

    public void refreshTimeStamp(){
        this.timeStamp = new Date();
    }
}
