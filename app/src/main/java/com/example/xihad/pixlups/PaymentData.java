package com.example.xihad.pixlups;

public class PaymentData {

    String account,paymentmethod;
    int balance;

    public PaymentData() {
    }

    public PaymentData(String account, String paymentmethod, int balance) {
        this.account = account;
        this.paymentmethod = paymentmethod;
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
