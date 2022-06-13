package com.example.bankclient;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable{

    private static final long serialVersionUID = 123456L;

    String accountID = null;
    String accountName = null;
    BigDecimal accountBalance = null;
    String firstName = null;
    String lastName = null;
    String address = null;
    String city = null;
    String country = null;
    String phoneNumber = null;
    String email = null;

    private final double HANGSOLAISUAT = 0.035;

    public Account() {
    }

    public Account(String accountID, String accountName, BigDecimal accountBalance, String firstName, String lastName, String address, String city, String country, String phoneNumber, String email) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Account(String accountID, String accountName, String firstName, String lastName, String address, String city, String country, String phoneNumber, String email) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.accountBalance = new BigDecimal("50000.0");
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Account {" +
                "accountID= " + accountID + '\'' +
                ", tenTK= " + accountName + '\'' +
                ", soTienTrongTK= " + accountBalance + " VNĐ" +
                ", firstName= " + firstName + '\'' +
                ", lastName= " + lastName + '\'' +
                ", address= " + address + '\'' +
                ", city= " + city + '\'' +
                ", country= " + country + '\'' +
                ", SDT= " + phoneNumber +
                ", email= " + email +
                "}";
    }

//    public void daoHan(){
//        int tienDaoHan = (int) (this.soTienTrongTK * HANGSOLAISUAT);
//        this.soTienTrongTK = this.soTienTrongTK + tienDaoHan;
//        System.out.println("Bạn vừa nhận được: " + tienDaoHan + " từ đáo hạn 1 tháng");
//    }
}
