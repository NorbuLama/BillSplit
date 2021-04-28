package com.example.billsplit;

public class UserInformation {
    String uid;
    String balance;

    public UserInformation() {
    }

    public UserInformation(String uid, String balance) {
        this.uid = uid;
        this.balance = balance;
    }

    public String getUid() {
        return uid;
    }

    public String getBalance() {
        return balance;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
