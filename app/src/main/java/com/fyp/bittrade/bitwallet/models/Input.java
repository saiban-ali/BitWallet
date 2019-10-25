package com.fyp.bittrade.bitwallet.models;

import com.google.gson.annotations.SerializedName;

public class Input {

    @SerializedName("transaction")
    private String transactionId;
    @SerializedName("index")
    private int index;
    @SerializedName("amount")
    private double amount;
    @SerializedName("address")
    private String address;
    @SerializedName("send")
    private boolean send;
    @SerializedName("signature")
    private String signature;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
