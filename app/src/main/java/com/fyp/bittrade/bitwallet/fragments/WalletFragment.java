package com.fyp.bittrade.bitwallet.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.bittrade.bitwallet.helpers.ClipboardHandler;
import com.fyp.bittrade.bitwallet.helpers.IFragmentCallback;
import com.fyp.bittrade.bitwallet.R;
import com.fyp.bittrade.bitwallet.models.Balance;
import com.fyp.bittrade.bitwallet.models.User;
import com.fyp.bittrade.bitwallet.viewmodels.UserViewModel;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {


    public WalletFragment() {
        // Required empty public constructor
    }

    private IFragmentCallback fragmentCallback;
    private TextView balanceText;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            fragmentCallback = (IFragmentCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IFragmentCallBack");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        UserViewModel userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        balanceText = view.findViewById(R.id.txt_cash_coin);
        balanceText.setText(String.format(
                Locale.US,
                "%f NC",
                userViewModel.getUser().getBalance()
        ));

        userViewModel.getBalance().observe(getActivity(), new Observer<Balance>() {
            @Override
            public void onChanged(Balance balance) {
                balanceText.setText(String.format(
                        Locale.US,
                        "%f NC",
                        balance.getBalance()
                ));
            }
        });

        final TextView address = view.findViewById(R.id.txt_address);
        address.setText(String.format(
                Locale.US,
                "%s",
                userViewModel.getUser().getWallet().getAddresses()[0]
        ));

        address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_DOWN) return false;
                ClipboardHandler.setClipboard(getActivity(), address.getText().toString());
                Toast.makeText(getActivity(), "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

//        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_transactions);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        GroupAdapter<GroupieViewHolder> adapter = new GroupAdapter<>();
//        recyclerView.setAdapter(adapter);
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(false));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(false));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(false));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(false));
//        adapter.add(new TransactionItem(true));
//        adapter.add(new TransactionItem(true));

        Button sendButton = view.findViewById(R.id.btn_send);
        Button receiveButton = view.findViewById(R.id.btn_receive);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallback.gotoSendFragment();
            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallback.gotoReceiveFragment();
            }
        });

        return view;
    }

//    class TransactionItem extends Item {
//
//        private boolean isReceived;
//
//        TransactionItem(boolean isReceived) {
//            this.isReceived = isReceived;
//        }
//
//        @Override
//        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
//            ImageView imageView = viewHolder.itemView.findViewById(R.id.img_trans_icon);
//            TextView desc = viewHolder.itemView.findViewById(R.id.txt_trans_desc);
//            TextView date = viewHolder.itemView.findViewById(R.id.txt_trans_date);
//            TextView amount = viewHolder.itemView.findViewById(R.id.txt_trans_amount);
//            TextView coin = viewHolder.itemView.findViewById(R.id.txt_trans_amount_coin);
//
//            if (isReceived) {
//                imageView.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
//                String temp = "+ 0.02 USD";
//                amount.setText(temp);
//                amount.setTextColor(Color.GREEN);
//                String temp2 = "0.001 B";
//                coin.setText(temp2);
//                String temp3 = "Received from 61616516";
//                desc.setText(temp3);
//            } else {
//                imageView.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
//                String temp = "- 0.01245 USD";
//                amount.setText(temp);
//                amount.setTextColor(Color.RED);
//                String temp2 = "0.00126 B";
//                coin.setText(temp2);
//                String temp3 = "Send to 61616516";
//                desc.setText(temp3);
//            }
//
//            String strDate = "Fab 16, 2019";
//            date.setText(strDate);
//        }
//
//        @Override
//        public int getLayout() {
//            return R.layout.cardview_transaction;
//        }
//    }

}
