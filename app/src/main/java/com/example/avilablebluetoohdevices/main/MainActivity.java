package com.example.avilablebluetoohdevices.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.avilablebluetoohdevices.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    BluetoothAdapter bluetoothAdapter;
    BluetoothManager bluetoothManager;
    List<String> saveList = new ArrayList();
    Set<BluetoothDevice> pairedDevices;
    Button bTurnOn, bTurnOff, bDiscoverable, bGetPairedDevices;
    ImageView blueImage;

    public static final int REQUEST_ENABLE_BT = 0;
    public static final int REQUEST_DISCOVERE_BT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        bTurnOn = findViewById(R.id.bTurnOn);
        bTurnOff = findViewById(R.id.bTurnOff);
        bDiscoverable = findViewById(R.id.bDiscoverable);
        bGetPairedDevices = findViewById(R.id.bGetPairedDevices);
        blueImage = findViewById(R.id.blueImage);

      /*  bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        saveList = new ArrayList();*/

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {

            Toast.makeText(this, "Bluetooth is Not Avilable", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Bluetooth is Avilable", Toast.LENGTH_SHORT).show();
        }

        if (bluetoothAdapter.isEnabled()) {

            blueImage.setImageResource(R.drawable.ic_baseline_bluetooth);

        }

        bTurnOn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

                if (!bluetoothAdapter.isEnabled()) {
                    showTostMessage("Turning On Bluetooh");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                } else {
                    showTostMessage("Bluetooh is Already On");

                }

            }
        });

       bTurnOff.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("MissingPermission")
           @Override
           public void onClick(View view) {

               if (bluetoothAdapter.isEnabled()) {
                   bluetoothAdapter.disable();
                   showTostMessage("Turning Bluetooh Off");
                   blueImage.setImageResource(R.drawable.ic_launcher_foreground);

               } else {
                   showTostMessage("Bluetooh is Already Off");

               }
           }
       });

        bDiscoverable.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

                if (!bluetoothAdapter.isDiscovering()) {
                    showTostMessage("Make your Devices DISCOVERABLE");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVERE_BT);
                } else {
                    showTostMessage("Bluetooh is Already On");

                }
            }
        });

      bGetPairedDevices.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              getPairedDevices();

          }
      });
    }

    public void showTostMessage(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case REQUEST_ENABLE_BT:
                if(requestCode == REQUEST_ENABLE_BT){

                    blueImage.setImageResource(R.drawable.ic_baseline_bluetooth);
                    showTostMessage("Bluetooh is Already On");

                }else{

                    showTostMessage("Turning On Bluetooh");
                }
                break;
        }
    }


    @SuppressLint("MissingPermission")
    public void getPairedDevices(){


        if (bluetoothAdapter.isEnabled()) {

            //bluetoothAdapter.enable();

            pairedDevices = bluetoothAdapter.getBondedDevices();
            for(BluetoothDevice bd : pairedDevices){
                saveList.add(bd.getName());
            }
            Adapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,saveList);
            listView.setAdapter((ListAdapter) arrayAdapter);

            showTostMessage("get Paired Devices");

        }else{

            showTostMessage("Trun On Bluetooh to get Paired Devices");
        }
    }
}