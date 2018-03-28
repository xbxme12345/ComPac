package com.example.huangy4.compac;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by huid on 3/28/2018.
 */

public class AlertDialogUtil {
    public static void showDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }
}
