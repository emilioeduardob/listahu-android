package com.dotech_hosting.listahu;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dotech_hosting.listahu.broadcast_receivers.AlarmReceiver;
import com.dotech_hosting.listahu.models.Denuncia;
import com.dotech_hosting.listahu.services.CallDetectService;
import com.dotech_hosting.listahu.support.SyncManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_SYSTEM_ALERT_WINDOW = 1;

    @Bind(R.id.button_toggle_service)
    public Button bnToggleService;
    @Bind(R.id.textViewCantidadDenuncias)
    public TextView mCantidadDenuncias;
    @Bind(R.id.textViewLastUpdate)
    public TextView mLastUpdate;

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
        updateUI();
        if (!AlarmReceiver.isAlarmSetUp(this)) {
            AlarmReceiver.setUpAlarm(this);
        }
    }

    private void updateUI() {
        Realm realm = Realm.getDefaultInstance();
        try {
            long recordCount = realm.where(Denuncia.class)
                    .count();
            mCantidadDenuncias.setText(Long.toString(recordCount));
            setLastUpdateTime(realm);
        } finally {
            realm.close();
        }
    }

    private void setLastUpdateTime(Realm realm) {
        RealmResults<Denuncia> records = realm.where(Denuncia.class)
                .findAll();
        records.sort("added", false);
        if (!records.isEmpty()) {
            Denuncia denuncia = records.get(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            Date added = new Date();
            try {
                added = format.parse(denuncia.getAdded());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String when = (String) DateUtils.getRelativeDateTimeString(this, added.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0);
            mLastUpdate.setText(when);
        } else {
            // register update
            sync();
        }
    }

    @OnClick(R.id.buttonVerDenuncias)
    public void openDenuncias() {
        Intent i = new Intent(this, DenunciasActivity.class);
        startActivity(i);
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
        new SyncManager(this).sync();
    }
}
