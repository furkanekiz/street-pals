package com.furkanekiz.streetpals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.furkanekiz.streetpals.databinding.ActivityMainBinding;
import com.furkanekiz.streetpals.view.MainMenuFragment;
import com.furkanekiz.streetpals.view.SpinnerFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private final boolean DEBUG = false;
    private SpinnerFragment mSpinnerFragment;
    public static FragmentManager manager;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mSpinnerFragment = new SpinnerFragment();
        manager = getSupportFragmentManager();

        //show spinner
        showLoadingSpinner();

        //add handler and open Main menu
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            hideLoadingSpinner();
            openMainMenu();
        }, 1500);

    }

    private void openMainMenu() {
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        manager.beginTransaction().replace(R.id.fragMain, mainMenuFragment).commitAllowingStateLoss();

    }

    private void showLoadingSpinner() {
        Log("showLoadingSpinner");
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fragMain, mSpinnerFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void hideLoadingSpinner() {
        Log("hideLoadingSpinner");
        try {
            manager.beginTransaction().remove(mSpinnerFragment).commit();
        } catch (Exception e) {
            Log("HideSpinnerError: " + e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        displayExitAppWarn();
    }

    private void displayExitAppWarn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Base_Theme_ALERT);
        builder.setTitle(R.string.string_app_warning);
        builder.setMessage(R.string.string_app_exit);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton(R.string.yes,
                (dialog, which) -> {

                    finishAffinity();
                    finishAndRemoveTask();
                });
        builder.setNegativeButton(R.string.no,
                (dialog, which) -> dialog.dismiss());

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).requestFocus();
        alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.white));
        alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.white));
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(R.drawable.alert_background);

    }

    private void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }

}