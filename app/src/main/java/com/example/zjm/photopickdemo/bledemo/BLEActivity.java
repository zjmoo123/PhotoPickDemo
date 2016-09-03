package com.example.zjm.photopickdemo.bledemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zjm.photopickdemo.R;
import com.example.zjm.photopickdemo.blechat.DeviceListActivity;

import java.util.ArrayList;
import java.util.List;

public class BLEActivity extends AppCompatActivity {
    private final static String TAG="BLEActivity";
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 3;
    private ListView mDeviceList;
    private Button scan;
    private DeviceListAdapter deviceListAdapter;
    private boolean mScanning=false;
    private List<String> devicelist;
    private Handler mHandler=new Handler();


    private BluetoothAdapter.LeScanCallback mLeScanCallback =

            new BluetoothAdapter.LeScanCallback() {


                @Override
                public void onLeScan(final BluetoothDevice bluetoothDevice, final int i, byte[] bytes) {
                    runOnUiThread(new Runnable() {

                        @Override

                        public void run() {

//                            if (bluetoothDevice!=null){
//
//                                    devicelist.add(bluetoothDevice.getName());
//                                mDeviceList.setAdapter(deviceListAdapter);
//                                    deviceListAdapter.setNotify();
//                                }
                            if (!devicelist.contains(bluetoothDevice.getName()+"\n"+bluetoothDevice.getAddress())){
                                devicelist.add(bluetoothDevice.getName()+"\n"+bluetoothDevice.getAddress());
                                deviceListAdapter.setNotify();
                            }





                         //   mNewDevicesArrayAdapter.notifyDataSetChanged();

                        }

                    });
                }



    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble2);
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {

            Toast.makeText(this,"ble不支持", Toast.LENGTH_SHORT).show();

            finish();

        }
        final BluetoothManager bluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();
        if(!mBluetoothAdapter.isEnabled()) {

            if (!mBluetoothAdapter.isEnabled()){

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            }

        }
        mDeviceList=(ListView)findViewById(R.id.device_list);
        scan=(Button)findViewById(R.id.scan);
        devicelist=new ArrayList<>();
        if (!devicelist.contains("xx")){
            Log.e(TAG,"bubaohan");
        }
        deviceListAdapter=new DeviceListAdapter(this,devicelist,R.layout.device_name);
        mDeviceList.setAdapter(deviceListAdapter);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                devicelist.clear();
                deviceListAdapter.setNotify();
                mHandler.postDelayed(new Runnable() {

                    @Override

                    public void run() {

                        mScanning = false;

                        mBluetoothAdapter.stopLeScan(mLeScanCallback);

                        Toast.makeText(BLEActivity.this, "时间到，扫描完成", Toast.LENGTH_SHORT).show();

                    }

                }, 10000);



                mScanning = true;

                mBluetoothAdapter.startLeScan(mLeScanCallback);
            }
        });






    }
}
