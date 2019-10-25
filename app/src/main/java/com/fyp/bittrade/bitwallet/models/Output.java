package com.fyp.bittrade.bitwallet.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Output {
    @SerializedName("amount")
    private BigDecimal amount;
    @SerializedName("address")
    private String address;

    public double getAmount() {
        return amount.doubleValue();
    }

    public void setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
