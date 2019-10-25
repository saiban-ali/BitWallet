package com.fyp.bittrade.bitwallet.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Wallet implements Parcelable  {

    @SerializedName("id")
    private String walletId;

    @SerializedName("addresses")
    private String[] addresses;

    protected Wallet(Parcel in) {
        walletId = in.readString();
        addresses = in.createStringArray();
    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    public void addAddress(String address) {
        String[] newArray = new String[addresses.length+1];
        for (int i = 0; i < addresses.length; i++) {
            newArray[i] = addresses[i];
        }
        newArray[newArray.length-1] = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(walletId);
        dest.writeStringArray(addresses);
    }
}
