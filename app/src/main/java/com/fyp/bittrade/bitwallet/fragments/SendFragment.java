package com.fyp.bittrade.bitwallet.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fyp.bittrade.bitwallet.helpers.IFragmentCallback;
import com.fyp.bittrade.bitwallet.R;
import com.fyp.bittrade.bitwallet.helpers.IResponseCallback;
import com.fyp.bittrade.bitwallet.models.TransactionResponse;
import com.fyp.bittrade.bitwallet.viewmodels.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends Fragment {

    private IFragmentCallback fragmentCallback;
    private TextInputLayout toAddress, amount;
    private MaterialButton sendButton;
    private UserViewModel userViewModel;

    public SendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            fragmentCallback = (IFragmentCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IFragmentCallBack");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        Button qrButton = view.findViewById(R.id.btn_scan);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallback.gotoScanFragment();
            }
        });

        sendButton = view.findViewById(R.id.btn_send);
        toAddress = view.findViewById(R.id.edittext_address);
        amount = view.findViewById(R.id.edittext_amount);

        setupSendButton();

        if (getArguments() != null) {
            String sendAddress = getArguments().getString("sendAddress");
            if (sendAddress != null && !sendAddress.equals("")) {
                toAddress.getEditText().setText(sendAddress);
            }
        }

        return view;
    }

    private void setupSendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    sendCoins();
                }
            }
        });
    }

    private void sendCoins() {
        userViewModel.sendCoins(
                toAddress.getEditText().getText().toString().trim(),
                Double.parseDouble(amount.getEditText().getText().toString()),
                new IResponseCallback<TransactionResponse>() {
                    @Override
                    public void onResponseSuccessful(TransactionResponse responseBody) {
                        userViewModel.setBalance(
                                responseBody.getData().getOutputs().get(UserViewModel.CHANGE).getAmount()
                        );
                        Toast.makeText(getActivity(), "Amount Send!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseUnsuccessful(ResponseBody responseBody) {
                        Toast.makeText(getActivity(), "Response Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseFailed(Throwable throwable) {
                        Toast.makeText(getActivity(), "Network Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private boolean validateFields() {
        double sendAmount = Double.parseDouble(amount.getEditText().getText().toString());
        String sendAddress = toAddress.getEditText().getText().toString();
        if (sendAddress.trim().length() != 64) {
            toAddress.setError("Invalid Address (must be 64 characters)");
            return false;
        }

        if (sendAmount < 1) {
            amount.setError("Invalid Amount (minimum is 1)");
            return false;
        }

        if (sendAmount > userViewModel.getUser().getBalance()) {
            amount.setError("Invalid Amount (Don't Enter More Than What You Have!)");
            return false;
        }

        return true;
    }

}
