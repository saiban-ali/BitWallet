package com.fyp.bittrade.bitwallet.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Balance implements Parcelable {

    @SerializedName("balance")
    private BigDecimal balance;

    public Balance(double balance) {
        this.balance = BigDecimal.valueOf(balance);
    }

    protected Balance(Parcel in) {
        balance = BigDecimal.valueOf(in.readDouble());
    }

    public static final Creator<Balance> CREATOR = new Creator<Balance>() {
        @Override
        public Balance createFromParcel(Parcel in) {
            return new Balance(in);
        }

        @Override
        public Balance[] newArray(int size) {
            return new Balance[size];
        }
    };

    public double getBalance() {
        return balance.doubleValue();
    }

    public void setBalance(double balance) {
        this.balance = BigDecimal.valueOf(balance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(balance.doubleValue());
    }
}
