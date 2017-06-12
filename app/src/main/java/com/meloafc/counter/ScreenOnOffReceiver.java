package com.meloafc.counter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.meloafc.counter.model.Event;
import com.meloafc.counter.util.Constants;

import java.util.Date;

public class ScreenOnOffReceiver extends BroadcastReceiver {

    private final DatabaseReference activeScreenReference;
    private Event event;

    public ScreenOnOffReceiver() {
        activeScreenReference = FirebaseDatabase.getInstance().getReference().child(Constants.DEV).child(Constants.FIREBASE_CHILD_ACTIVE_SCREEN);
        startEvent();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            startEvent();

            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            endEvent();
            saveEvent();
            event = null;
        }
    }

    private void startEvent() {
        this.event = new Event(new Date().getTime());
    }

    private void endEvent() {
        if(this.event != null) {
            event.setFinalDate(new Date().getTime());
            event.calculateDuration();
        }
    }

    private void saveEvent() {
        if(event == null || event.isValid() == false) {
            return;
        }

        // Creating new node, which returns the unique key value
        String id = activeScreenReference.push().getKey();

        // pushing node using the id
        activeScreenReference.child(id).setValue(event);
    }
}
