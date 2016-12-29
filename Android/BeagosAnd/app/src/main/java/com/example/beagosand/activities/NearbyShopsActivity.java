package com.example.beagosand.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.example.beagosand.R;
import com.example.beagosand.adapters.NearbyShopsAdapter;
import com.example.beagosand.models.Shop;
import com.example.beagosand.utils.FontsOverride;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import io.realm.Realm;

public class NearbyShopsActivity extends AppCompatActivity implements BeaconConsumer {

    ArrayList<Shop> shops;
    RecyclerView rv_shops;
    BeaconManager beaconManager;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    HashMap<String, Region> ssnRegionMap;
    private static final Identifier nameSpaceId = Identifier.parse("0x5dc33487f02e477d4058");
    public ArrayList<String> regionNameList;
    public ArrayList<Region> regionList;
    // boolean f = false;

    private static String[] mPermissions = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_shops);


        beaconManager = BeaconManager.getInstanceForApplication(this);
        //  BeagosApp.getInstance().onListRefreshListener = onListRefreshListener;
        //  BeagosApp.getInstance().context = this;

        //This is done in every activity to change the font.

        ssnRegionMap = new HashMap<>();
        regionList = new ArrayList<>();
        regionNameList = new ArrayList<>();


//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                try {
//                    sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };


        if (!isBlueEnable()) {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(bluetoothIntent);
        }

        if (ActivityCompat.checkSelfPermission(NearbyShopsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NearbyShopsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
            return;
        }


        ssnRegionMap.put("0x0117c59825E9", new Region("Test Room", nameSpaceId, Identifier.parse("0x0117c59825E9"), null));
        ssnRegionMap.put("0x0117c55be3a8", new Region("Git Room", nameSpaceId, Identifier.parse("0x0117c55be3a8"), null));
        ssnRegionMap.put("0x0117c552c493", new Region("Android Room", nameSpaceId, Identifier.parse("0x0117c552c493"), null));
        ssnRegionMap.put("0x0117c55fc452", new Region("iOS Room", nameSpaceId, Identifier.parse("0x0117c55fc452"), null));
        ssnRegionMap.put("0x0117c555c65f", new Region("Python Room", nameSpaceId, Identifier.parse("0x0117c555c65f"), null));
        ssnRegionMap.put("0x0117c55d6660", new Region("Office", nameSpaceId, Identifier.parse("0x0117c55d6660"), null));
        ssnRegionMap.put("0x0117c55ec086", new Region("Ruby Room", nameSpaceId, Identifier.parse("0x0117c55ec086"), null));

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        new BackgroundPowerSaver(this);
        beaconManager.bind(this);
        Log.d("Added beacon", "Added beacon");

        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA, getWindow());

        initShops();
        rv_shops = (RecyclerView) findViewById(R.id.activity_nearby_shops_rv);
        rv_shops.setAdapter(new NearbyShopsAdapter(this, shops));
        rv_shops.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    private void initShops() {
        Realm realm = Realm.getDefaultInstance();
        this.shops = new ArrayList<>();
        this.shops.addAll(realm.where(Shop.class).findAll());

    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {

                //Log.d("In here","Connected a beacon");
                //String regname = region.getUniqueId();


            }

            @Override
            public void didExitRegion(Region region) {

                Log.d("OUt here", "Not connected a beacon");
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {
                String regname = region.getUniqueId();

                switch (i) {


                    case INSIDE:
                        regionList.add(region);
                        sleepforasecond(regname);

                        //Toast.makeText(getApplicationContext(),"NO BEACON",Toast.LENGTH_LONG).show();
                }

            }


        });


        try {
            for (String key : ssnRegionMap.keySet()) {
                Region region = ssnRegionMap.get(key);
                beaconManager.startMonitoringBeaconsInRegion(region);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean isBlueEnable() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return bluetoothAdapter.isEnabled();

    }


    public void sleepforasecond(String regname) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        if (regname.equals("Android Room")) {
            regionNameList.add(regname);
            Intent intent = new Intent(NearbyShopsActivity.this, ShopDetailsActivity.class);
            intent.putExtra("UUID", "1");
            intent.putExtra("source", "abcd");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (regname.equals("Python Room")) {
            regionNameList.add(regname);
            Intent intent = new Intent(NearbyShopsActivity.this, ShopDetailsActivity.class);
            intent.putExtra("UUID", "2");
            intent.putExtra("source", "abcd");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (regname.equals("iOS Room")) {
            regionNameList.add(regname);
            Intent intent = new Intent(NearbyShopsActivity.this, ShopDetailsActivity.class);
            intent.putExtra("UUID", "3");
            intent.putExtra("source", "abcd");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (regname.equals("Ruby Room")) {
            regionNameList.add(regname);
            Intent intent = new Intent(NearbyShopsActivity.this, ShopDetailsActivity.class);
            intent.putExtra("UUID", "4");
            intent.putExtra("source", "abcd");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (regname.equals("Git Room")) {
            regionNameList.add(regname);
            Intent intent = new Intent(NearbyShopsActivity.this, ShopDetailsActivity.class);
            intent.putExtra("UUID", "6");
            intent.putExtra("source", "abcd");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("DONT KNOW", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }


}