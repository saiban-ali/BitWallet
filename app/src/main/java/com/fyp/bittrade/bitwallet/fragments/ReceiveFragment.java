package com.fyp.bittrade.bitwallet.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fyp.bittrade.bitwallet.R;
import com.fyp.bittrade.bitwallet.helpers.ClipboardHandler;
import com.fyp.bittrade.bitwallet.viewmodels.UserViewModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Locale;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiveFragment extends Fragment {


    public ReceiveFragment() {
        // Required empty public constructor
    }

    private ImageView qrCode;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive, container, false);

        UserViewModel userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        qrCode = view.findViewById(R.id.qr_code);
        generateQRCode(userViewModel.getUser().getWallet().getAddresses()[0]);

        final TextView walletAddress = view.findViewById(R.id.wallet_address);
        final TextView amount = view.findViewById(R.id.amount_coin);
        amount.setText(String.format(
                Locale.US,
                "%f NC",
                userViewModel.getUser().getBalance()
        ));
        walletAddress.setText(userViewModel.getUser().getWallet().getAddresses()[0]);
        walletAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_DOWN) return false;
                ClipboardHandler.setClipboard(getActivity(), walletAddress.getText().toString());
                Toast.makeText(getActivity(), "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }

    private void generateQRCode(String id) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(id, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? BLACK : WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            //Log.e("QR ERROR", ""+e);
            Toast.makeText(getActivity(), "Could not generate QR Code", Toast.LENGTH_LONG).show();
        }
    }

}
