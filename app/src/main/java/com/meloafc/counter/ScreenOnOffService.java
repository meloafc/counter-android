package com.meloafc.counter;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class ScreenOnOffService extends Service {

    private ScreenOnOffReceiver mScreenReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        registerScreenStatusReceiver();
    }

    @Override
    public void onDestroy() {
        unregisterScreenStatusReceiver();
    }

    private void registerScreenStatusReceiver() {
        mScreenReceiver = new ScreenOnOffReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mScreenReceiver, filter);

        Log.i("LOG","IntentFilter registrado");
    }

    private void unregisterScreenStatusReceiver() {
        try {
            if (mScreenReceiver != null) {
                unregisterReceiver(mScreenReceiver);
                Log.i("LOG","IntentFilter removido");
            }
        } catch (IllegalArgumentException e) {}
    }
}
