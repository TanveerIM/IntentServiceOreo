package com.tanveerm.intentserviceoreo;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import static com.tanveerm.intentserviceoreo.App.CHANNEL_ID;

public class ExpIntentService extends IntentService {
    private static final String TAG = "expIntentService";

    private PowerManager.WakeLock wakeLock;

    public ExpIntentService() {
        super("ExpIntentService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "Exp: Wakelock");
        wakeLock.acquire();
        Log.e("TAG", "Wakelock acquired");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example IntentService")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_android)
                    .build();

            startForeground(1, notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");

        wakeLock.release();
        Log.e("TAG", "Wakelock released");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG, "onHandleIntent");

        String input = intent.getStringExtra("inputExtra");

        for (int i = 0; i < 10 ; i++) {
            Log.e(TAG, input + " - " + i);
            SystemClock.sleep(1000);
        }

    }
}
