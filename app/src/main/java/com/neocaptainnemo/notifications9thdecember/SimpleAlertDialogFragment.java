package com.neocaptainnemo.notifications9thdecember;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SimpleAlertDialogFragment extends DialogFragment {

    public static final String KEY_RESULT = "SimpleAlertDialogFragment";
    public static final String ARG_BUTTON = "ARG_BUTTON";


    interface ClickListener {
        void onPositiveClicked();

        void onNegativeClicked();

        void onNeutralClicked();
    }

    private static final String ARG_TITLE = "ARG_TITLE";

    public static SimpleAlertDialogFragment newInstance(String title) {
        SimpleAlertDialogFragment dialogFragment = new SimpleAlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Bundle bundle = new Bundle();
                bundle.putInt(ARG_BUTTON, i);

                getParentFragmentManager()
                        .setFragmentResult(KEY_RESULT, bundle);
            }
        };

        return new AlertDialog.Builder(requireContext())
                .setIcon(R.drawable.ic_baseline_4k_24)
                .setTitle(requireArguments().getString(ARG_TITLE))
                .setMessage(R.string.message)
                .setPositiveButton(R.string.ok, clickListener)
                .setNegativeButton(R.string.no, clickListener)
                .setNeutralButton(R.string.maybe, clickListener)
                .setCancelable(false)
                .create();
    }
}
