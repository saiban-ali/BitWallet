package com.fyp.bittrade.bitwallet.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private Wallet wallet;
    private Balance balance;

    public User() {
    }

    protected User(Parcel in) {
        wallet = in.readParcelable(Wallet.class.getClassLoader());
        balance = in.readParcelable(Balance.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public double getBalance() {
        return balance.getBalance();
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(wallet, flags);
        dest.writeParcelable(balance, flags);
    }
}
