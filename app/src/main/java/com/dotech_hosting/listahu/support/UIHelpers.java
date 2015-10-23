package com.dotech_hosting.listahu.support;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dotech_hosting.listahu.MainApp;
import com.dotech_hosting.listahu.R;

/**
 * Created by emilio on 10/23/15.
 */
public class UIHelpers {
    public static void showToast(String message) {
        Toast toast = new Toast(MainApp.getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(buildLayout(message));
        toast.show();
    }

    public static View buildLayout(String incomingNumber) {
        Context context = MainApp.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_warning, null);

        return layout;
    }
}
