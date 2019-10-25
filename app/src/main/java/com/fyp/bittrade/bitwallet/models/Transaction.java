package com.fyp.bittrade.bitwallet.models;

import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("fromAddress")
    private String fromAddress;
    @SerializedName("toAddress")
    private String toAddress;
    @SerializedName("changeAddress")
    private String changeAddress;
    @SerializedName("amount")
    private double amount;

    public Transaction(String toAddress, String fromAddress, String changeAddress, double amount) {
        this.toAddress = toAddress;
        this.fromAddress = fromAddress;
        this.changeAddress = changeAddress;
        this.amount = amount;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getChangeAddress() {
        return changeAddress;
    }

    public void setChangeAddress(String changeAddress) {
        this.changeAddress = changeAddress;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
