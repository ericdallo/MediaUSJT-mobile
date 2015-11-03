package com.mediausjt.util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.mediausjt.R;

public class MediaDialog {

    public static void showDialog(Context context, String title, String message) {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setButton("OK", (dialog, which) -> {
            });
            alertDialog.setIcon(R.drawable.icon_alert_info);
            alertDialog.show();
        } catch (Exception e) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void showSnack(View view, int message, int time) {
        Snackbar snackbar = Snackbar.make(view, message, time);
        snackbar.getView().setBackgroundColor(Color.WHITE);
        snackbar.show();
    }
}
