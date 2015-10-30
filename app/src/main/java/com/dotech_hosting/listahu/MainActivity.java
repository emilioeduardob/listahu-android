package com.dotech_hosting.listahu;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dotech_hosting.listahu.services.CallDetectService;
import com.dotech_hosting.listahu.services.SyncService;
import com.dotech_hosting.listahu.support.AppHelpers;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_SYSTEM_ALERT_WINDOW = 1;

    @Bind(R.id.button_toggle_service)
    public Button bnToggleService;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        running = isMyServiceRunning(CallDetectService.class);
        if (running) {
            bnToggleService.setText(R.string.stop_monitor);
        }
    }

    @OnClick(R.id.button_toggle_service)
    public void toggleListener() {
        running = isMyServiceRunning(CallDetectService.class);

        Intent i = new Intent(this, CallDetectService.class);
        if (running) {
            stopService(i);
            bnToggleService.setText(R.string.start_monitor);
        } else {
            startService(i);
            sync();
            requestPopupPermissions();
            bnToggleService.setText(R.string.stop_monitor);
        }
    }

    private void requestPopupPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Por favor. Habilite Dibujar para mostrar Notificaciones de sistema.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_SYSTEM_ALERT_WINDOW);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SYSTEM_ALERT_WINDOW) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    Toast.makeText(this, "Permiso para mostrar notificaciones aceptado", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void sync() {
        new SyncService(this).sync();
    }
}
