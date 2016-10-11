package com.example.zjm.photopickdemo.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.zjm.photopickdemo.R;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magneticSensor;
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;

    private static final float NS2S = 1.0f / 1000000000.0f;

    private float timestamp;
    private float angle[] = new float[3];

    private TextView mGText_x;
    private TextView mGText_y;
    private TextView mGText_z;
    private CameraView cameraView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        //   showTextView = (TextView) findViewById(R.id.showTextView);

        mGText_x=(TextView)findViewById(R.id.x);
        mGText_y=(TextView)findViewById(R.id.y);
        mGText_z=(TextView)findViewById(R.id.z);
        cameraView=(CameraView)findViewById(R.id.camera_view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magneticSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//注册陀螺仪传感器，并设定传感器向应用中输出的时间间隔类型是SensorManager.SENSOR_DELAY_GAME(20000微秒)
//SensorManager.SENSOR_DELAY_FASTEST(0微秒)：最快。最低延迟，一般不是特别敏感的处理不推荐使用，该模式可能在成手机电力大量消耗，由于传递的为原始数据，诉法不处理好会影响游戏逻辑和UI的性能
//SensorManager.SENSOR_DELAY_GAME(20000微秒)：游戏。游戏延迟，一般绝大多数的实时性较高的游戏都是用该级别
//SensorManager.SENSOR_DELAY_NORMAL(200000微秒):普通。标准延时，对于一般的益智类或EASY级别的游戏可以使用，但过低的采样率可能对一些赛车类游戏有跳帧现象
//SensorManager.SENSOR_DELAY_UI(60000微秒):用户界面。一般对于屏幕方向自动旋转使用，相对节省电能和逻辑处理，一般游戏开发中不使用
        sensorManager.registerListener(this, gyroscopeSensor,
                SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magneticSensor,
                SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float a = (float) Math.sqrt(x * x + y * y + z * z);

            System.out.println("a---------->" + a);
// 传感器从外界采集数据的时间间隔为10000微秒
            System.out.println("magneticSensor.getMinDelay()-------->"
                    + accelerometerSensor.getMinDelay());
// 加速度传感器的最大量程
            System.out.println("event.sensor.getMaximumRange()-------->"
                    + sensorEvent.sensor.getMaximumRange());

            System.out.println("x------------->" + x);
            System.out.println("y------------->" + y);
            System.out.println("z------------->" + z);

            Log.d("jarlen", "x------------->" + x);
            Log.d("jarlen", "y------------>" + y);
            Log.d("jarlen", "z----------->" + z);

        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // 手机的磁场感应器从外部采集数据的时间间隔是10000微秒
            System.out.println("magneticSensor.getMinDelay()-------->"
                    + magneticSensor.getMinDelay());
// 磁场感应器的最大量程
            System.out.println("event.sensor.getMaximumRange()----------->"
                    + sensorEvent.sensor.getMaximumRange());
            System.out.println("x------------->" + x);
            System.out.println("y------------->" + y);
            System.out.println("z------------->" + z);
//
// Log.d("TAG","x------------->" + x);
// Log.d("TAG", "y------------>" + y);
// Log.d("TAG", "z----------->" + z);
//
// showTextView.setText("x---------->" + x + "\ny-------------->" +
// y + "\nz----------->" + z);
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            if (timestamp != 0) {
                final float dT = (sensorEvent.timestamp - timestamp) * NS2S;
                //弧度
                angle[0] += sensorEvent.values[0] * dT;
                angle[1] += sensorEvent.values[1] * dT;
                angle[2] += sensorEvent.values[2] * dT;

                // 将弧度转化为角度
                float anglex = ((float) Math.toDegrees(angle[0]))%360;
                float angley = ((float) Math.toDegrees(angle[1]))%360;
                float anglez = ((float) Math.toDegrees(angle[2]))%360;

                System.out.println("anglex------------>" + anglex);
                System.out.println("angley------------>" + angley);
                System.out.println("anglez------------>" + anglez);
                mGText_x.setText("anglex-->" + anglex);
                mGText_y.setText("angley-->" + angley);
                mGText_z.setText("anglez-->" + anglez);
                cameraView.setRotate(anglex,angley,anglez);
                System.out.println("gyroscopeSensor.getMinDelay()----------->" +
                        gyroscopeSensor.getMinDelay());

            }
            timestamp = sensorEvent.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
