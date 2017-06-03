package com.meloafc.counter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.meloafc.counter.model.Evento;
import com.meloafc.counter.util.Constants;

import java.util.Calendar;

public class ScreenOnOffReceiver extends BroadcastReceiver {

    private DatabaseReference mDatabase;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("LOG",intent.getAction());

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            salvar(Evento.Type.ACTION_SCREEN_OFF);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            salvar(Evento.Type.ACTION_SCREEN_ON);
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        }
    }

    private void salvar(Evento.Type type){
        Calendar agora = Calendar.getInstance();

        String dia = String.valueOf(agora.get(Calendar.DATE));
        String mes = String.valueOf(agora.get(Calendar.MONTH)+1);
        String ano = String.valueOf(agora.get(Calendar.YEAR));

        Log.i("LOG",dia+"/"+mes+"/"+ano + " - evento: "+ type);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference mRef = mDatabase.child(ano).child(mes).child(dia);
        DatabaseReference mRef = mDatabase.child(Constants.FIREBASE_CHILD_EVENTOS);

        // Creating new node, which returns the unique key value
        String id = mRef.push().getKey();

        // creating object
        Evento evento = new Evento(agora.getTimeInMillis(), type);

        // pushing node using the id
        mRef.child(id).setValue(evento);
    }
}
