package com.dotech_hosting.listahu.support;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dotech_hosting.listahu.MainApp;
import com.dotech_hosting.listahu.R;
import com.dotech_hosting.listahu.models.Denuncia;

/**
 * Created by emilio on 10/23/15.
 */
public class AppHelpers {
    private final Denuncia mDenuncia;

    public AppHelpers(Denuncia denuncia) {
       mDenuncia = denuncia;
    }

    public void NotifyUser() {
        showNotification();
        showSystemAlert();
    }

    private void showSystemAlert() {
        Context ctx = MainApp.getContext();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        final WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        final View myView = buildLayout();
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                wm.removeView(myView);
                return true;
            }
        });

        // Add layout to window manager
        wm.addView(myView, params);
    }

    private void showNotification() {
        Context ctx = MainApp.getContext();
        Notification noti =
                new NotificationCompat.Builder(MainApp.getContext())
                        .setSmallIcon(buildImageResource())
                        .setContentTitle("Lista Hü")
                        .setContentText(buildMessage())
                        .setPriority(Notification.PRIORITY_MAX)
                        .setAutoCancel(true)
                        .build();

        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, noti);
    }

    public View buildLayout() {
        Context context = MainApp.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_warning, null);

        TextView textViewMessage = (TextView) layout.findViewById(R.id.textViewMensaje);
        ImageView imageViewIcono = (ImageView) layout.findViewById(R.id.imageIconType);

        textViewMessage.setText(buildMessage());
        imageViewIcono.setImageResource(buildImageResource());

        return layout;
    }

    private int buildImageResource() {
        if (mDenuncia.getTipo().equals("Extorsión")) {
            return R.drawable.ic_icon_evil;
        } else {
            return R.drawable.ic_icon_warning;
        }
    }

    private String buildMessage() {
        String message = "Atención! El número " + mDenuncia.getNumero();
        message += " esta denunciado como " + mDenuncia.getTipo();

        return message;
    }
}
