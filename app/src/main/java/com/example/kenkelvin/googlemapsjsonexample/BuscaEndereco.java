package com.example.kenkelvin.googlemapsjsonexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Created by KenKelvin on 15/05/2015.
 */
public class BuscaEndereco extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void lancaNotificacao(String msg){
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setContentTitle("Geocoder...");
        b.setContentText(msg);
        b.setSmallIcon(android.R.drawable.ic_dialog_map);

        Notification not = b.build();

        NotificationManager notMgr =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notMgr.notify(1,not);
    }

    private AjaxCallback<JSONObject> callback = new AjaxCallback<JSONObject>(){
        @Override
        public void callback(String url, JSONObject object, AjaxStatus status) {
            try {
                JSONArray results = object.getJSONArray("results");
                JSONObject endereco = results.getJSONObject(0);
                String retorno = endereco.getString("formatted_address");
                lancaNotificacao(retorno);
                MainActivity.resultado.setText(retorno);
            } catch (JSONException e){
                lancaNotificacao(e.getMessage());
            } finally {
                Intent intent = new Intent("buscouendereco");
                sendBroadcast(intent);
                stopSelf();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String lat = intent.getStringExtra("lat");
        String lng = intent.getStringExtra("lng");
        String urlConexao =
                "http://maps.googleapis.com/maps/api/geocode/json?address="+lat+","+lng;
        AQuery aq = new AQuery(this);
        aq.ajax(urlConexao, JSONObject.class, callback);

        return super.onStartCommand(intent, flags, startId);
    }
}
