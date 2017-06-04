package com.meloafc.counter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.meloafc.counter.model.Event;
import com.meloafc.counter.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScreenOnOffReceiver extends BroadcastReceiver {

    private final DatabaseReference eventsReference;
    private final DatabaseReference dailySummaryReference;
    private final DatabaseReference totalReference;

    public ScreenOnOffReceiver() {
        eventsReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_EVENTOS);
        dailySummaryReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_RESUMO_DIARIO);
        totalReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_TOTAL);

        eventsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event savedEvent = dataSnapshot.getValue(Event.class);
                saveDailySummary(savedEvent);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            saveEvent(Event.Type.ACTION_SCREEN_OFF);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            saveEvent(Event.Type.ACTION_SCREEN_ON);

            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
    }

    private void saveEvent(Event.Type type) {
        Calendar now = Calendar.getInstance();

        // Creating new node, which returns the unique key value
        String id = eventsReference.push().getKey();

        // creating object
        Event event = new Event(now.getTimeInMillis(), type);

        // pushing node using the id
        eventsReference.child(id).setValue(event);
        increaseTotal("Event");
        increaseTotal("Event_"+ event.getType().getName());
    }

    private void saveDailySummary(Event event) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = sdf.format(new Date(event.getDate()));

        DatabaseReference dayReference = dailySummaryReference.child(formattedDate).child("Event_"+ event.getType().getName());

        dayReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    private void increaseTotal(String name) {
        DatabaseReference totalNameReference = totalReference.child(name);
        totalNameReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }
}
