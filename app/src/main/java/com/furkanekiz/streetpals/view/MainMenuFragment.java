
package com.furkanekiz.streetpals.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.furkanekiz.streetpals.fact.view.FactFragment;
import com.furkanekiz.streetpals.R;
import com.furkanekiz.streetpals.call.CallFragment;
import com.furkanekiz.streetpals.databinding.MainMenuFragmentBinding;
import com.furkanekiz.streetpals.home.view.HomeFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainMenuFragment extends Fragment {

    private static final String TAG = MainMenuFragment.class.getSimpleName();
    private static final boolean DEBUG = false;
    public static FragmentManager manager;
    View generalView;
    MainMenuFragmentBinding mainMenuFragmentBinding;
    private int lastSelectedItemId = -1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainMenuFragmentBinding = MainMenuFragmentBinding.inflate(inflater, container, false);
        generalView = mainMenuFragmentBinding.getRoot();
        Log("onCreate");

        manager = getChildFragmentManager();

        mainMenuFragmentBinding.imgLogo.setAlpha(0.5f);
        mainMenuFragmentBinding.bottomNavMain.setOnItemSelectedListener(onNavigationItemSelectedListener);
        mainMenuFragmentBinding.bottomNavMain.setSelectedItemId(R.id.homeFragment);

        return generalView;
    }

    private final NavigationBarView.OnItemSelectedListener onNavigationItemSelectedListener = item -> {
        int selectedItemId = item.getItemId();

        if (selectedItemId != lastSelectedItemId) {
            if (selectedItemId == R.id.homeFragment) {
                loadFragment(new HomeFragment());
            } else if (selectedItemId == R.id.factFragment) {
               loadFragment(new FactFragment());
            } else if (selectedItemId == R.id.callFragment) {
                loadFragment(new CallFragment());
            }

            lastSelectedItemId = selectedItemId;
        }
        item.setChecked(true);

        return true;
    };


    public static void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction().setCustomAnimations(R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out);
        transaction.replace(R.id.fragMain, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();

    }

    private static void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }

}
