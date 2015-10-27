package com.dotech_hosting.listahu;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dotech_hosting.listahu.services.CallDetectService;
import com.dotech_hosting.listahu.services.SyncService;
import com.dotech_hosting.listahu.support.AppHelpers;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
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
            bnToggleService.setText("Detener Servicio");
        }
    }

    @OnClick(R.id.button_toggle_service)
    public void toggleListener() {
        running = isMyServiceRunning(CallDetectService.class);

        Intent i = new Intent(this, CallDetectService.class);
        if (running) {
            Log.d(TAG, "Estaba corriendo");
            AppHelpers.showToast("Deteniendo servicio");
            stopService(i);
            bnToggleService.setText("Iniciar Servicio");
        } else {
            Log.d(TAG, "NO Estaba corriendo");
            AppHelpers.showToast("Iniciando servicio");
            startService(i);
            sync();
            bnToggleService.setText("Detener Servicio");
        }
    }

    @OnClick(R.id.fab)
    public void mensajeAction(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

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

    public void sync() {
        new SyncService(this).sync();
    }
}
