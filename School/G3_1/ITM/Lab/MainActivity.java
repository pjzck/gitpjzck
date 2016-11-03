package com.example.pjzck.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager sm;
    private MSensorEventListener sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button show_button = (Button) findViewById(R.id.show_button);
        show_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView show_text = (TextView)findViewById(R.id.textView);
                EditText edit_text = (EditText)findViewById(R.id.editText);

                show_text.setText(edit_text.getText());
            }
        });

        sel = new MSensorEventListener();
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
/*
    public void show_click(View view){
        TextView show_text = (TextView)findViewById(R.id.textView);
        EditText edit_text = (EditText)findViewById(R.id.editText);

        show_text.setText(edit_text.getText());
    }
*/

    @Override
    protected void onResume(){
        Sensor accelerometerSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(sel, accelerometerSensor, sm.SENSOR_DELAY_NORMAL);

        Sensor magneticSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sm.registerListener(sel, magneticSensor, sm.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    private final class MSensorEventListener implements SensorEventListener{
        @Override
        public void onSensorChanged(SensorEvent event){
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float x = event.values[sm.DATA_X];
                float y = event.values[sm.DATA_Y];
                float z = event.values[sm.DATA_Z];

                TextView ax = (TextView)findViewById(R.id.Gravity_X);
                TextView ay = (TextView)findViewById(R.id.Gravity_Y);
                TextView az = (TextView)findViewById(R.id.Gravity_Z);

                ax.setText("X:" + x);
                ay.setText("Y:" + y);
                az.setText("Z:" + z);

            }
            else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                float x = event.values[sm.DATA_X];
                float y = event.values[sm.DATA_Y];
                float z = event.values[sm.DATA_Z];

                TextView mx = (TextView)findViewById(R.id.Magnetic_X);
                TextView my = (TextView)findViewById(R.id.Magnetic_Y);
                TextView mz = (TextView)findViewById(R.id.Magnetic_Z);

                mx.setText("X:" + x);
                my.setText("Y:" + y);
                mz.setText("Z:" + z);

            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy){}
    }

    @Override
    protected void onPause(){
        sm.unregisterListener(sel);
        super.onPause();
    }
}

