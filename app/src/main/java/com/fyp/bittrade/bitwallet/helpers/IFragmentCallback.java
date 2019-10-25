package com.fyp.bittrade.bitwallet.helpers;

public interface IFragmentCallback {

    void gotoSendFragment();
    void gotoReceiveFragment();
    void gotoScanFragment();
    void gotoSendFragment(String address);

}
