package com.example.pguti.appsoap;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class MainActivity extends AppCompatActivity {

    private static final String Metodo = "GetCitiesByCountry";

    private static final String namespace = "http://www.webserviceX.NET";

    private static final String accionSoap = "http://www.webserviceX.NET/GetCitiesByCountry";

    private static final String url = "http://www.webservicex.net/globalweather.asmx";
    private EditText edit;
    private TextView text;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = (EditText)findViewById(R.id.editext);
        text = (TextView)findViewById(R.id.text1);
        button = (Button)findViewById(R.id.boton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }
                    String pais = edit.getText().toString();


                    SoapObject request = new SoapObject(namespace, Metodo);
                    request.addProperty("CountryName", pais);


                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    sobre.dotNet = true;
                    sobre.setOutputSoapObject(request);


                    HttpTransportSE transporte = new HttpTransportSE(url);


                    transporte.call(accionSoap, sobre);


                    SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                    text.setText(resultado.toString());


                }catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        });


    }
}
