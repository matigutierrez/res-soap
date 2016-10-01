package com.example.pguti.aplirest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private Button boton;
    private TextView texto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button)findViewById(R.id.boton);
        texto = (TextView)findViewById(R.id.mostrar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerWS obtenerWS = new ObtenerWS();
                obtenerWS.execute();
                texto.setText("");
            }
        });
    }

    private class ObtenerWS extends AsyncTask<String,String,Boolean> {
        String resp;

        @Override
        protected Boolean doInBackground(String... params) {


            Boolean resul  =true;

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet url = new HttpGet ("http://indicadoresdeldia.cl/webservice/indicadores.json");

            url.setHeader ("content-type","application/json");

            try{
                HttpResponse respuesta = httpClient.execute(url);
                resp = EntityUtils.toString(respuesta.getEntity());
                JSONObject respJSON = new JSONObject(resp);


            }catch(Exception ex){
                Log.e("ServicioRest","Error!", ex);
                resul = false;
            }

            return resul;
        }

        protected void onPostExecute(Boolean result) {

            if (result)
            {
                texto.setText(resp);
            }
        }
    }
}
