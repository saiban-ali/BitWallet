package com.fyp.bittrade.bitwallet.api;

import com.fyp.bittrade.bitwallet.models.Address;
import com.fyp.bittrade.bitwallet.models.Balance;
import com.fyp.bittrade.bitwallet.models.Passphrase;
import com.fyp.bittrade.bitwallet.models.Transaction;
import com.fyp.bittrade.bitwallet.models.TransactionResponse;
import com.fyp.bittrade.bitwallet.models.Wallet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {

//    String BASE_URL = "https://bittradeapi.azurewebsites.net/api/";
    String BASE_URL = "http://192.168.10.17/";
//    String BASE_URL = "https://crucial-strata-250709.appspot.com/api/";
//    String BASE_URL = "https://bittrade-252909.appspot.com/api/";

    @GET("operator/wallets/{walletId}")
    Call<Wallet> getWallet(@Path("walletId") String wallet);

    @GET("operator/{addressId}/balance")
    Call<Balance> getBalance(@Path("addressId") String AddressId);

    @POST("operator/wallets")
    Call<Wallet> createWallet(@Body Passphrase passphrase);

    @POST("operator/wallets/{walletId}/addresses")
    Call<Address> generateAddress(@Header("password") String phrase, @Path("walletId") String walletId);

    @POST("operator/wallets/{walletId}/transactions")
    Call<TransactionResponse> sendCoins(
            @Path("walletId") String walletId,
            @Header("password") String password,
            @Body Transaction transaction
            );
}
