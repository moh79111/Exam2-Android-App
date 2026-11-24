package com.example.exam2;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private Button loginButton;
    private long lastBackPressTime = 0L;
    private Snackbar exitSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int savedMode = getSharedPreferences("settings", MODE_PRIVATE)
                .getInt("mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(savedMode);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        TextWatcher validationWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateLoginButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed.
            }
        };

        emailInput.addTextChangedListener(validationWatcher);
        passwordInput.addTextChangedListener(validationWatcher);
        updateLoginButtonState();

        loginButton.setOnClickListener(view -> {
            if (!loginButton.isEnabled()) {
                return;
            }

            // This assignment demonstrates client-side validation only.
            // No server-side credential authentication is configured.
            passwordInput.setText("");
            startActivity(new Intent(MainActivity.this, ModeActivity.class));
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                long now = SystemClock.elapsedRealtime();
                if (lastBackPressTime != 0L && now - lastBackPressTime <= 2500L) {
                    if (exitSnackbar != null) {
                        exitSnackbar.dismiss();
                    }
                    finishAffinity();
                    return;
                }

                lastBackPressTime = now;
                exitSnackbar = Snackbar.make(
                        findViewById(R.id.root),
                        "Press back again to exit",
                        Snackbar.LENGTH_INDEFINITE
                );
                exitSnackbar.setDuration(2500);
                exitSnackbar.show();
            }
        });
    }

    private void updateLoginButtonState() {
        String email = emailInput.getText() == null
                ? ""
                : emailInput.getText().toString().trim();
        String password = passwordInput.getText() == null
                ? ""
                : passwordInput.getText().toString();

        boolean validEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean validPassword = password.length() >= 6;
        loginButton.setEnabled(validEmail && validPassword);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lastBackPressTime = 0L;
    }

    @Override
    protected void onPause() {
        lastBackPressTime = 0L;
        if (exitSnackbar != null) {
            exitSnackbar.dismiss();
            exitSnackbar = null;
        }
        super.onPause();
    }
}
