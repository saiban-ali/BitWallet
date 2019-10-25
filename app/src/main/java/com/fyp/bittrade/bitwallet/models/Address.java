package com.fyp.bittrade.bitwallet.models;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("address")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
