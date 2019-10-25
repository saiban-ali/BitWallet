package com.fyp.bittrade.bitwallet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fyp.bittrade.bitwallet.R;
import com.fyp.bittrade.bitwallet.helpers.IResponseCallback;
import com.fyp.bittrade.bitwallet.helpers.PreferenceUtil;
import com.fyp.bittrade.bitwallet.models.Balance;
import com.fyp.bittrade.bitwallet.models.User;
import com.fyp.bittrade.bitwallet.models.Wallet;
import com.fyp.bittrade.bitwallet.viewmodels.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar loginProgress;
    private MaterialButton loginButton, createWalletButton;
    private UserViewModel userViewModel;

    @Override
    protected void onResume() {
        super.onResume();
        if (!PreferenceUtil.getWalletId(this).equals("")) {
            loginButton.performClick();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = ViewModelProviders.of(LoginActivity.this).get(UserViewModel.class);

        loginButton = findViewById(R.id.btn_enter_wallet);
        createWalletButton = findViewById(R.id.btn_create_wallet);

        loginProgress = findViewById(R.id.login_progress);
        loginProgress.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                loginProgress.setVisibility(View.VISIBLE);

                String address = ((TextInputLayout) findViewById(R.id.edittext_address)).getEditText().getText().toString();
                userViewModel.getUserFromServer(address, new IResponseCallback<User>() {
                    @Override
                    public void onResponseSuccessful(User responseBody) {
                        loginButton.setEnabled(true);
                        loginProgress.setVisibility(View.GONE);
                        if (PreferenceUtil.saveWalletId(responseBody.getWallet().getWalletId(), LoginActivity.this)) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("user", responseBody);
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onResponseUnsuccessful(ResponseBody responseBody) {
                        loginButton.setEnabled(true);
                        loginProgress.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Could not get the wallet", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponseFailed(Throwable throwable) {
                        loginButton.setEnabled(true);
                        loginProgress.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        createWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.dialog_create_wallet);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                FloatingActionButton nextButton = dialog.findViewById(R.id.btn_next);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextInputLayout phraseTextInput = dialog.findViewById(R.id.edittext_phrase);
                        String phrase = phraseTextInput.getEditText().getText().toString();
                        if (phrase.trim().equals("")) return;

                        if (checkPhraseValidity(phrase)) {
                            createWallet(phrase);
                            dialog.dismiss();
                        } else {
                            phraseTextInput.setError("Word count must be greater than 4");
                        }
                    }

                    private boolean checkPhraseValidity(String phrase) {
                        String[] chunks = phrase.split(" ");
                        return chunks.length > 4;
                    }
                });

                dialog.show();
            }
        });
    }

    private void createWallet(String phrase) {
        createWalletButton.setEnabled(false);
        loginProgress.setVisibility(View.VISIBLE);

        userViewModel.createWallet(phrase, new IResponseCallback<Wallet>() {
            @Override
            public void onResponseSuccessful(Wallet responseBody) {
                loginButton.setEnabled(true);
                loginProgress.setVisibility(View.GONE);
                if (PreferenceUtil.saveWalletId(responseBody.getWalletId(), LoginActivity.this)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    User user = new User();
                    user.setWallet(responseBody);
                    user.setBalance(new Balance(0.0));
                    bundle.putParcelable("user", user);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                }
            }

            @Override
            public void onResponseUnsuccessful(ResponseBody responseBody) {
                loginButton.setEnabled(true);
                loginProgress.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Could not create wallet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseFailed(Throwable throwable) {
                loginButton.setEnabled(true);
                loginProgress.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
