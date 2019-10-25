package com.fyp.bittrade.bitwallet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.fyp.bittrade.bitwallet.helpers.IFragmentCallback;
import com.fyp.bittrade.bitwallet.R;
import com.fyp.bittrade.bitwallet.fragments.ReceiveFragment;
import com.fyp.bittrade.bitwallet.fragments.ScanFragment;
import com.fyp.bittrade.bitwallet.fragments.SendFragment;
import com.fyp.bittrade.bitwallet.fragments.SettingsFragment;
import com.fyp.bittrade.bitwallet.fragments.WalletFragment;
import com.fyp.bittrade.bitwallet.models.User;
import com.fyp.bittrade.bitwallet.models.Wallet;
import com.fyp.bittrade.bitwallet.viewmodels.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity implements IFragmentCallback {

    private BottomNavigationViewEx bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStatusBarColor();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User user = bundle != null ? (User) bundle.getParcelable("user") : null;
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.setUser(user);

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.wallet:
                        fragment = new WalletFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.send:
                        fragment = new SendFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.scan:
                        fragment = new ScanFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.receive:
                        fragment = new ReceiveFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.settings:
                        fragment = new SettingsFragment();
                        loadFragment(fragment);
                        return true;
                    default:
                        return false;
                }
            }
        });

        bottomNav.setSelectedItemId(R.id.wallet);

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Window window = getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getColor(android.R.color.white));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.RED);
        }
    }

    @Override
    public void gotoSendFragment() {
        bottomNav.setSelectedItemId(R.id.send);
    }

    @Override
    public void gotoReceiveFragment() {
        bottomNav.setSelectedItemId(R.id.receive);
    }

    @Override
    public void gotoScanFragment() {
        bottomNav.setSelectedItemId(R.id.scan);
    }

    @Override
    public void gotoSendFragment(String address) {
        Fragment fragment = new SendFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sendAddress", address);
        fragment.setArguments(bundle);
        loadFragment(fragment);
        bottomNav.getMenu().findItem(R.id.send).setChecked(true);
    }
}
