package com.fyp.bittrade.bitwallet.models;

import com.google.gson.annotations.SerializedName;

public class TransactionResponse {

    @SerializedName("id")
    private String id;
    @SerializedName("hash")
    private String hash;
    @SerializedName("type")
    private String type;
    @SerializedName("data")
    private Data data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
