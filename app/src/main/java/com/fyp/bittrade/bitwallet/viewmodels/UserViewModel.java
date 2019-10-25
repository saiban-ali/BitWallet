package com.fyp.bittrade.bitwallet.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fyp.bittrade.bitwallet.helpers.IResponseCallback;
import com.fyp.bittrade.bitwallet.models.Address;
import com.fyp.bittrade.bitwallet.models.Balance;
import com.fyp.bittrade.bitwallet.models.TransactionResponse;
import com.fyp.bittrade.bitwallet.models.User;
import com.fyp.bittrade.bitwallet.models.Wallet;
import com.fyp.bittrade.bitwallet.repositories.WalletRepository;

import okhttp3.ResponseBody;

public class UserViewModel extends ViewModel {
    private WalletRepository walletRepository;
    private User user;
    private MutableLiveData<Balance> balanceMutableLiveData;
    public static int CHANGE = 1;

    public UserViewModel() {
        walletRepository = WalletRepository.getInstance();
        balanceMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Balance> getBalance() {
        return balanceMutableLiveData;
    }

    public void setBalance(double balance) {
        balanceMutableLiveData.setValue(new Balance(balance));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void getUserFromServer(String walletId, final IResponseCallback<User> callback) {
        if (user == null) {
            user = new User();
            walletRepository.getWallet(walletId, new IResponseCallback<Wallet>() {
                @Override
                public void onResponseSuccessful(Wallet responseBody) {
                    user.setWallet(responseBody);
                    getBalance(user.getWallet().getAddresses()[0], callback);
                }

                @Override
                public void onResponseUnsuccessful(ResponseBody responseBody) {
                    callback.onResponseUnsuccessful(responseBody);
                }

                @Override
                public void onResponseFailed(Throwable throwable) {
                    callback.onResponseFailed(throwable);
                }
            });
        }
    }

    private void getBalance(String address, final IResponseCallback<User> callback) {
        walletRepository.getBalance(address, new IResponseCallback<Balance>() {
            @Override
            public void onResponseSuccessful(Balance responseBody) {
                user.setBalance(responseBody);
                setBalance(responseBody.getBalance());
                callback.onResponseSuccessful(user);
            }

            @Override
            public void onResponseUnsuccessful(ResponseBody responseBody) {
                callback.onResponseUnsuccessful(responseBody);
            }

            @Override
            public void onResponseFailed(Throwable throwable) {
                callback.onResponseFailed(throwable);
            }
        });
    }

    public void createWallet(final String phrase, final IResponseCallback<Wallet> callback) {
        if (user == null) {
            user = new User();
        }
        walletRepository.createWallet(phrase, new IResponseCallback<Wallet>() {
            @Override
            public void onResponseSuccessful(Wallet responseBody) {
                generateAddress(phrase, responseBody, callback);
            }

            @Override
            public void onResponseUnsuccessful(ResponseBody responseBody) {
                callback.onResponseUnsuccessful(responseBody);
            }

            @Override
            public void onResponseFailed(Throwable throwable) {
                callback.onResponseFailed(throwable);
            }
        });
    }

    private void generateAddress(String phrase, final Wallet wallet, final IResponseCallback<Wallet> callback) {

        walletRepository.generateAddress(phrase, wallet.getWalletId(), new IResponseCallback<Address>() {
            @Override
            public void onResponseSuccessful(Address responseBody) {
                wallet.addAddress(responseBody.getAddress());
                user.setWallet(wallet);
                user.setBalance(new Balance(0.0));
                setBalance(0.0);
                callback.onResponseSuccessful(wallet);
            }

            @Override
            public void onResponseUnsuccessful(ResponseBody responseBody) {
                callback.onResponseUnsuccessful(responseBody);
            }

            @Override
            public void onResponseFailed(Throwable throwable) {
                callback.onResponseFailed(throwable);
            }
        });

    }

    public void sendCoins(String toAddress, double amount, final IResponseCallback<TransactionResponse> callback) {
        walletRepository.sendCoins(
                user.getWallet().getWalletId(),
                toAddress,
                user.getWallet().getAddresses()[0],
                amount,
                new IResponseCallback<TransactionResponse>() {
                    @Override
                    public void onResponseSuccessful(TransactionResponse responseBody) {
                        user.setBalance(new Balance(
                                responseBody.getData().getOutputs().get(CHANGE).getAmount()
                        ));
                        setBalance(responseBody.getData().getOutputs().get(CHANGE).getAmount());
                        callback.onResponseSuccessful(responseBody);
                    }

                    @Override
                    public void onResponseUnsuccessful(ResponseBody responseBody) {
                        callback.onResponseUnsuccessful(responseBody);
                    }

                    @Override
                    public void onResponseFailed(Throwable throwable) {
                        callback.onResponseFailed(throwable);
                    }
                }
        );
    }
}
