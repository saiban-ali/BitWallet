package com.fyp.bittrade.bitwallet.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("inputs")
    private List<Input> inputs;
    @SerializedName("outputs")
    private List<Output> outputs;

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }
}
