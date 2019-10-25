package com.fyp.bittrade.bitwallet.models;

import com.google.gson.annotations.SerializedName;

public class Passphrase {

    @SerializedName("password")
    private String password;

    public Passphrase(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
