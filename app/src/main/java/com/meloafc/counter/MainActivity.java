package com.meloafc.counter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_COUNTER_SERVICE = "MELOAFC_COUNTER_SERVICE";

    private Button botaoIniciarContagem;
    private Button botaoPararContagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.botaoIniciarContagem = (Button) findViewById(R.id.botaoIniciarContagem);
        this.botaoPararContagem = (Button) findViewById(R.id.botaoPararContagem);

        botaoIniciarContagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarContagem();
            }
        });

        botaoPararContagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pararContagem();
            }
        });
    }

    private void iniciarContagem() {
        if(isExecutandoServico(ScreenOnOffService.class)) {
            print("Serviço já está sendo executado");
            return;
        } else {
            print("Iniciando serviço");
        }

        Intent intent = new Intent(this, ScreenOnOffService.class);
        intent.addCategory(TAG_COUNTER_SERVICE);
        startService(intent);
    }

    private void pararContagem() {
        if(isExecutandoServico(ScreenOnOffService.class)) {
            print("Parando servico");
        } else {
            print("Serviço já está parado");
            return;
        }

        Intent intent = new Intent(this, ScreenOnOffService.class);
        intent.addCategory(TAG_COUNTER_SERVICE);
        stopService(intent);
    }

    private boolean isExecutandoServico(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void print(String mensagem) {
        Log.d("PRINT", mensagem);
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT).show();
    }
}
