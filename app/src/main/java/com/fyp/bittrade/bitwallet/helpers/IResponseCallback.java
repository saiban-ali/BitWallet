package com.fyp.bittrade.bitwallet.helpers;

import okhttp3.ResponseBody;

public interface IResponseCallback<T> {
    void onResponseSuccessful(T responseBody);
    void onResponseUnsuccessful(ResponseBody responseBody);
    void onResponseFailed(Throwable throwable);
}
