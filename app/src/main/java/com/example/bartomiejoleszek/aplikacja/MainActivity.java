package com.example.bartomiejoleszek.aplikacja;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager manager;

    Sensor temperatura = null;
    Sensor cisnienie = null;
    Sensor wilgotnosc = null;
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        TextView t5;




@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(TextView)findViewById(R.id.textView);
        t2=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView3);
        t4=(TextView)findViewById(R.id.textView4);
        t5=(EditText)findViewById(R.id.editText);


        manager=(SensorManager)getSystemService(SENSOR_SERVICE);
        /*List<Sensor> sensory = manager.getSensorList(Sensor.TYPE_ALL);
        for(int x=0;x<sensory.size();x++){
        t1.setText(t1.getText()+"\n"+sensory.get(x).getName());
        }*/

        wilgotnosc = manager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        temperatura = manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        cisnienie = manager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        manager.registerListener(this,wilgotnosc,SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this,temperatura,SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this,cisnienie,SensorManager.SENSOR_DELAY_NORMAL);

}


    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                onSensorChangedHumidity(event);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                onSensorChangedTemperature(event);
                break;
            case Sensor.TYPE_PRESSURE:
                onSensorChangedPressure(event);
                break;
    }}

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// TODO Auto-generated method stub
        }

    public void onSensorChangedHumidity(SensorEvent event) {
        t2.setText("Wilgotnosc : "+event.values[0]+ " %");

        }
    public void onSensorChangedTemperature(SensorEvent event) {
        t3.setText("Temperatura : "+event.values[0]+"  C");
        }

    public void onSensorChangedPressure(SensorEvent event) {
        t4.setText("Cisnienie : "+event.values[0] + " hPa");
    }

    public void Zapisz(View view) throws IOException {
        try {

            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("pogoda.txt",MODE_APPEND));
            String tekst =t5.getText().toString();;
            t5.setText(tekst);
            String text ="";
            text = tekst+'\n'+t2.getText().toString()+'\n'+t3.getText().toString()+'\n'+t4.getText().toString()+'\n';
            out.write(text);
            out.write('\n');
            out.close();

            Toast.makeText(this,"Zapisano !",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            Toast.makeText(this,"Błąd zapisu",Toast.LENGTH_LONG).show();
        }

}

    public void Odczytaj(View view) {
        StringBuilder text = new StringBuilder();
        try {
            InputStream instream = openFileInput("pogoda.txt");
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line=null;
                while (( line = buffreader.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }}}catch (IOException e) {
            e.printStackTrace();
        }
        TextView tv = (TextView) findViewById(R.id.textView5);
        tv.setText(text);
    }
    }

