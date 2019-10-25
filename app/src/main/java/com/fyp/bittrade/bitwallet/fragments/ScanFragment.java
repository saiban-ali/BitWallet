package com.fyp.bittrade.bitwallet.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fyp.bittrade.bitwallet.helpers.IFragmentCallback;
import com.fyp.bittrade.bitwallet.helpers.PermissionsHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private IFragmentCallback fragmentCallback;
    private ZXingScannerView scannerView;

    private ArrayList<Integer> mSelectedIndices;

    public ScanFragment() {
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
        scannerView = new ZXingScannerView(getActivity());

        setupFormats();
        return scannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        PermissionsHandler.verifyCameraPermissions(getActivity());
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<>();
//        formats.add(BarcodeFormat.QR_CODE);
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<>();
            for(int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
//            if (ZXingScannerView.ALL_FORMATS.get(index) == BarcodeFormat.QR_CODE) {
//
//            }
        }
        if(scannerView != null) {
            scannerView.setFormats(formats);
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), rawResult.getText(), Toast.LENGTH_LONG).show();
        scannerView.resumeCameraPreview(this);
        fragmentCallback.gotoSendFragment(rawResult.getText());
    }
}
