package com.fyp.bittrade.bitwallet.repositories;

import android.provider.Telephony;

import com.fyp.bittrade.bitwallet.api.Client;
import com.fyp.bittrade.bitwallet.helpers.IResponseCallback;
import com.fyp.bittrade.bitwallet.models.Address;
import com.fyp.bittrade.bitwallet.models.Balance;
import com.fyp.bittrade.bitwallet.models.Passphrase;
import com.fyp.bittrade.bitwallet.models.Transaction;
import com.fyp.bittrade.bitwallet.models.TransactionResponse;
import com.fyp.bittrade.bitwallet.models.Wallet;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletRepository {

    private static WalletRepository INSTANCE = null;

    public static WalletRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WalletRepository();
        }
        return INSTANCE;
    }

    public void getWallet(String walletId, final IResponseCallback<Wallet> callback) {
        Call<Wallet> call = Client.getInstance().getClient().getWallet(walletId);
        call.enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(@NotNull Call<Wallet> call, @NotNull Response<Wallet> response) {
                if (response.isSuccessful()) {
                    callback.onResponseSuccessful(response.body());
                } else {
                    callback.onResponseUnsuccessful(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Wallet> call, @NotNull Throwable t) {
                callback.onResponseFailed(t);
            }
        });
    }

    public void getBalance(String addressId, final IResponseCallback<Balance> callback) {
        Call<Balance> call = Client.getInstance().getClient().getBalance(addressId);
        call.enqueue(new Callback<Balance>() {
            @Override
            public void onResponse(@NotNull Call<Balance> call, @NotNull Response<Balance> response) {
                if (response.isSuccessful()) {
                    callback.onResponseSuccessful(response.body());
                } else {
                    callback.onResponseUnsuccessful(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Balance> call, @NotNull Throwable t) {
                callback.onResponseFailed(t);
            }
        });
    }

    public void createWallet(String phrase, final IResponseCallback<Wallet> callback) {
        Call<Wallet> call = Client.getInstance().getClient().createWallet(new Passphrase(phrase));
        call.enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(@NotNull Call<Wallet> call, @NotNull Response<Wallet> response) {
                if (response.isSuccessful()) {
                    callback.onResponseSuccessful(response.body());
                } else {
                    callback.onResponseUnsuccessful(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Wallet> call, @NotNull Throwable t) {
                callback.onResponseFailed(t);
            }
        });
    }

    public void generateAddress(String phrase, String walletId, final IResponseCallback<Address> callback) {

        Call<Address> call = Client.getInstance().getClient().generateAddress(phrase, walletId);
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(@NotNull Call<Address> call, @NotNull Response<Address> response) {
                if (response.isSuccessful()) {
                    callback.onResponseSuccessful(response.body());
                } else {
                    callback.onResponseUnsuccessful(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Address> call, @NotNull Throwable t) {
                callback.onResponseFailed(t);
            }
        });

    }

    public void sendCoins(
            String walletId,
            String toAddress,
            String fromAddress,
            double amount,
            final IResponseCallback<TransactionResponse> callback
    ) {
        Transaction transaction = new Transaction(toAddress, fromAddress, fromAddress, amount);
        Call<TransactionResponse> call = Client.getInstance().getClient().sendCoins(
                walletId,
                "password",
                transaction
        );

        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(@NotNull Call<TransactionResponse> call, @NotNull Response<TransactionResponse> response) {
                if (response.isSuccessful()) {
                    callback.onResponseSuccessful(response.body());
                } else {
                    callback.onResponseUnsuccessful(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NotNull Call<TransactionResponse> call, @NotNull Throwable t) {
                callback.onResponseFailed(t);
            }
        });
    }
}
