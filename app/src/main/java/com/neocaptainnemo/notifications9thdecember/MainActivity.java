package com.neocaptainnemo.notifications9thdecember;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements SimpleAlertDialogFragment.ClickListener {

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private int notificationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationChannelCompat channel = new NotificationChannelCompat.Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_DEFAULT)
                .setDescription(getString(R.string.channel_description))
                .setName(getString(R.string.channel_name))
                .build();

        NotificationManagerCompat.from(this).createNotificationChannel(channel);

        getSupportFragmentManager()
                .setFragmentResultListener(SimpleAlertDialogFragment.KEY_RESULT, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        switch (result.getInt(SimpleAlertDialogFragment.ARG_BUTTON)) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(MainActivity.this, "Positive", Toast.LENGTH_SHORT).show();
                                break;

                            case DialogInterface.BUTTON_NEUTRAL:
                                Toast.makeText(MainActivity.this, "Neutral", Toast.LENGTH_SHORT).show();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(MainActivity.this, "Negative", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                });

        findViewById(R.id.toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast();
            }
        });

        findViewById(R.id.snackbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar(view);
            }

        });

        findViewById(R.id.snackbar_with_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbarWithAction(view);
            }
        });


        findViewById(R.id.alert_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        findViewById(R.id.alert_dialog_custom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogCustomView();
            }
        });

        findViewById(R.id.dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogFragment();
            }
        });

        findViewById(R.id.dialog_fragment_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogFragmentCustom();
            }
        });

        findViewById(R.id.dialog_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });

        findViewById(R.id.notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });

        findViewById(R.id.dialog_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogList();
            }
        });

        findViewById(R.id.dialog_single_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSingleChoice();
            }
        });

        findViewById(R.id.dialog_multiple_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogMultipleChoice();
            }
        });

    }

    private void showDialogMultipleChoice() {
        CharSequence[] options = {"one", "two", "three", "four"};
        boolean [] selected = {false, false, false, false};

        new AlertDialog.Builder(this)
                .setMultiChoiceItems(options, selected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        selected[i] = b;
                    }
                })
                .setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        StringBuilder builder = new StringBuilder();

                        for (int j = 0; j < options.length; j++) {
                            if (selected[j]) {
                                builder.append(options[j]);
                                builder.append(",");
                            }
                        }

                        Toast.makeText(MainActivity.this, builder.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
                .show();
    }

    private void showDialogSingleChoice() {
        CharSequence[] options = {"one", "two", "three", "four"};

        final int[] selectedIndex = {-1};

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(options, selectedIndex[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedIndex[0] = i;
                    }
                })
                .setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, options[selectedIndex[0]], Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }

    private void showDialogList() {
        CharSequence[] options = {"one", "two", "three", "four"};
        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, options[i], Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void showNotification() {

        notificationId++;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification compat = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Content Title")
                .setContentText("Content Text")
                .setSmallIcon(R.drawable.ic_baseline_4k_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(notificationId, compat);

    }

    private void showBottomSheet() {
        CustomBottomSheetDialogFragment.newInstance()
                .show(getSupportFragmentManager(), "CustomBottomSheetDialogFragment");
    }

    private void showDialogFragmentCustom() {
        if (getSupportFragmentManager().findFragmentByTag("CustomViewDialogFragment") == null) {
            CustomViewDialogFragment.newInstance()
                    .show(getSupportFragmentManager(), "CustomViewDialogFragment");
        }
    }

    private void showDialogFragment() {

        SimpleAlertDialogFragment.newInstance("Some title")
                .show(getSupportFragmentManager(), "SimpleAlertDialogFragment");

    }

    private void showAlertDialogCustomView() {
        View customTitle = getLayoutInflater().inflate(R.layout.dialog_custom_title, null);

        View customView = getLayoutInflater().inflate(R.layout.dialog_custom_view, null);


        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText customEdit = customView.findViewById(R.id.edit_dialog);

                        Toast.makeText(MainActivity.this, customEdit.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCustomTitle(customTitle)
                .setView(customView)
                .show();

    }

    private void showAlertDialog() {

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_4k_24)
                .setTitle(R.string.title)
                .setMessage(R.string.message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton(R.string.maybe, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Maybe", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .show();

    }

    private void showSnackbarWithAction(View view) {
        Snackbar.make(findViewById(R.id.coordinator), R.string.snackbar_message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showToast();
                    }
                })
                .show();

    }

    private void showToast() {
        Toast.makeText(getApplicationContext(), R.string.toast_message, Toast.LENGTH_SHORT).show();
    }

    private void showSnackbar(View view) {
        Snackbar.make(view, R.string.snackbar_message, Snackbar.LENGTH_INDEFINITE).show();

    }

    @Override
    public void onPositiveClicked() {

        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNegativeClicked() {

    }

    @Override
    public void onNeutralClicked() {

    }
}