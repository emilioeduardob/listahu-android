package com.dotech_hosting.listahu.support;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dotech_hosting.listahu.DenunciasActivity;
import com.dotech_hosting.listahu.MainApp;
import com.dotech_hosting.listahu.R;
import com.dotech_hosting.listahu.models.Denuncia;

/**
 * Created by emilio on 10/23/15.
 */
public class AppHelpers {
    private final Denuncia mDenuncia;
    private final Context context;

    public AppHelpers(Denuncia denuncia) {
        mDenuncia = denuncia;
        this.context = MainApp.getContext();
    }

    public void NotifyUser() {
        showNotification();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(context)) {
                showSystemAlert();
            }
        } else {
            showSystemAlert();
        }
    }

    private void showSystemAlert() {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        final View notificationView = buildLayout();
        windowManager.addView(notificationView, params);

        notificationView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                windowManager.removeView(notificationView);
                return true;
            }
        });

    }

    private void showNotification() {
        Intent intent = new Intent(context, DenunciasActivity.class);
        intent.putExtra("numero", mDenuncia.getNumero());
        int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
        PendingIntent pIntent = PendingIntent.getActivity(context, requestID, intent, flags);

        Notification noti =
                new NotificationCompat.Builder(MainApp.getContext())
                        .setSmallIcon(buildImageResource())
                        .setContentTitle("Lista Hü")
                        .setContentText(buildMessage())
                        .setContentIntent(pIntent)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setAutoCancel(true)
                        .build();

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, noti);
    }

    public View buildLayout() {
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
        message += " fue denunciado como " + mDenuncia.getTipo();

        return message;
    }
}
