package com.furkanekiz.streetpals.call;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.furkanekiz.streetpals.R;
import com.furkanekiz.streetpals.databinding.CallFragmentBinding;

public class CallFragment extends Fragment {

    private static final String TAG = CallFragment.class.getSimpleName();
    private static final boolean DEBUG = true;
    CallFragmentBinding callFragmentBinding;
    private static final int CALL_PERMISSION_REQUEST_CODE = 123;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        callFragmentBinding = CallFragmentBinding.inflate(inflater, container, false);
        View generalView = callFragmentBinding.getRoot();


        callFragmentBinding.txtCall.setOnClickListener(view -> makeEmergencyCall());
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        } else {

            callFragmentBinding.txtCall.setVisibility(View.VISIBLE);

            makeEmergencyCall();
        }

        return generalView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                callFragmentBinding.txtCall.setVisibility(View.VISIBLE);
                makeEmergencyCall();
            } else {
                callFragmentBinding.txtCall.setVisibility(View.GONE);
                Toast.makeText(getContext(), R.string.string_app_no_permission, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makeEmergencyCall() {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:153"));
        startActivity(intent);
    }

    private static void Log(String message) {
        if (DEBUG)
            Log.d(TAG, message);
    }
}


