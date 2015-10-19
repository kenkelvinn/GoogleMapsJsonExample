package com.example.kenkelvin.googlemapsjsonexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private EditText edtLat;
    private EditText edtLng;
    private Button botao;
    public static TextView resultado;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            botao.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtLat = (EditText)findViewById(R.id.edtLatitude);
        edtLng = (EditText)findViewById(R.id.edtLongitude);
        botao = (Button)findViewById(R.id.botao);
        resultado = (TextView)findViewById(R.id.location);

        IntentFilter filtro = new IntentFilter("buscouendereco");
        registerReceiver(receiver, filtro);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public void buscarEndereco(View v){
        botao.setEnabled(false);
        Intent intent = new Intent(this, BuscaEndereco.class);
        intent.putExtra("lat", edtLat.getText().toString());
        intent.putExtra("lng", edtLng.getText().toString());
        startService(intent);
    }
}
