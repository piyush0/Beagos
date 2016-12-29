package com.example.beagosand;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.content.SharedPreferences;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.beagosand.activities.NearbyShopsActivity;
import com.example.beagosand.activities.ShopDetailsActivity;
import com.example.beagosand.models.Shop;
import com.example.beagosand.utils.FontsOverride;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import io.realm.Realm;


/**
 * Created by piyush0 on 29/12/16.
 */

public class BeagosApp extends Application {

    //    private static BeagosApp instance = null;
//    private BeaconManager beaconManager;
//    private static final Identifier nameSpaceId = Identifier.parse("0x5dc33487f02e477d4058");
//
//    public CopyOnWriteArrayList<Region> regionList;
//    public HashMap<String,Region> ssnRegionMap;
//    public OnListRefreshListener onListRefreshListener;
//    public NearbyShopsActivity context;
//
//    public interface OnListRefreshListener {
//        void onListRefresh();
//    }
//
//    public static BeagosApp getInstance() {
//        return instance;
//    }
//
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/" + FontsOverride.FONT_PROXIMA_NOVA);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (!sharedPreferences.getBoolean("firstTime", false)) {

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            Shop s1 = new Shop("Coding Ninjas", 70.0F, "Android Room", "1");
            realm.copyToRealm(s1);
            Shop s2 = new Shop("Ventursity", 42.2F, "Python Room", "2");
            realm.copyToRealm(s2);
            Shop s3 = new Shop("DTU", 31.4F, "iOS Room", "3");
            realm.copyToRealm(s3);
            Shop s4 = new Shop("NSIT", 77.5F, "Ruby Room", "4");
            realm.copyToRealm(s4);
            Shop s5 = new Shop("IITD", 77.5F, "Office", "5");
            realm.copyToRealm(s5);
            Shop s6 = new Shop("IIITD", 69.5F, "Git Room", "6");
            realm.copyToRealm(s6);
            Shop s7 = new Shop("Office", 78.5F, "Test Room", "7");
            realm.copyToRealm(s7);

            realm.commitTransaction();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }


//        instance = this;
//        //check signed in
//        setUpBeacon();
    }
//
//    public void setUpBeacon(){
//        ssnRegionMap = new HashMap<>();
//        regionList = new CopyOnWriteArrayList<>();
//
//        ssnRegionMap.put("0x0117c59825E9",new Region("Test Room",nameSpaceId, Identifier.parse("0x0117c59825E9"),null));
//        ssnRegionMap.put("0x0117c55be3a8",new Region("Git Room",nameSpaceId,Identifier.parse("0x0117c55be3a8"),null));
//        ssnRegionMap.put("0x0117c552c493",new Region("Android Room",nameSpaceId,Identifier.parse("0x0117c552c493"),null));
//        ssnRegionMap.put("0x0117c55fc452",new Region("iOS Room",nameSpaceId,Identifier.parse("0x0117c55fc452"),null));
//        ssnRegionMap.put("0x0117c555c65f",new Region("Python Room",nameSpaceId,Identifier.parse("0x0117c555c65f"),null));
//        ssnRegionMap.put("0x0117c55d6660",new Region("Office",nameSpaceId,Identifier.parse("0x0117c55d6660"),null));
//        ssnRegionMap.put("0x0117c55ec086",new Region("Ruby Room",nameSpaceId,Identifier.parse("0x0117c55ec086"),null));
//
//        beaconManager = BeaconManager.getInstanceForApplication(context);
//        beaconManager.getBeaconParsers().add(new BeaconParser().
//                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
//        new BackgroundPowerSaver(this);
//        beaconManager.bind(this);
//
//    }
//
//
//    @Override
//    public void onBeaconServiceConnect() {
//        beaconManager.addMonitorNotifier(new MonitorNotifier() {
//            @Override
//            public void didEnterRegion(Region region) {
//
//            }
//
//            @Override
//            public void didExitRegion(Region region) {
//
//            }
//
//            @Override
//            public void didDetermineStateForRegion(int i, Region region) {
//
//                String regionName = region.getUniqueId();
//                String beaconSSN = region.getId2().toHexString();
//
//                switch (i){
//                    case INSIDE:
//                        Log.i("TAG","Enter " + regionName);
//                        regionList.add(region);
//                        BeagosApp.notifyListChange();
//                        if(regionName.equals("Python Room"))
//                        {
//                            Intent intent = new Intent(getApplicationContext(),ShopDetailsActivity.class);
//                            intent.putExtra("UUID","0x0117c555c65f");
//                            intent.putExtra("source","BeagosApp");
//                            startActivity(intent);
//                        }
//                        else if(regionName.equals("Android Room")) {
//                            Intent intent = new Intent(getApplicationContext(),ShopDetailsActivity.class);
//                            intent.putExtra("UUID", "0x0117c552c493");
//                            intent.putExtra("source","BeagosApp");
//                            startActivity(intent);
//                        }
//                        // Toast.makeText(getApplicationContext(),"Found beacon",Toast.LENGTH_SHORT).show();
//                        // MyApp.showNotification("Found beacon");
//                        //enterRegion(beaconSSN);
//                        break;
//                    case OUTSIDE:
//                        Log.i("TAG","Outside " + regionName);
//                        if(regionList.contains(region)) {
//                            regionList.remove(region);
//                            BeagosApp.notifyListChange();
//                        }
//                        //exitRegion(beaconSSN);
//                        //  MyApp.showNotification("Exit beacon");
//                        // Toast.makeText(getApplicationContext(),"Exit beacon",Toast.LENGTH_SHORT).show();
//                        break;
//                }
//
//                ArrayList<String> list_beaconSSN = new ArrayList<String>();
//                for(Region r: regionList){
//                    list_beaconSSN.add(r.getId2().toHexString());
//                }
//
//            }
//        });
//
//        try {
//            for(String key:ssnRegionMap.keySet()) {
//                Region region = ssnRegionMap.get(key);
//                beaconManager.startMonitoringBeaconsInRegion(region);
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    private static void showNotification(String message){
//        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(instance)
//                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
//                .setContentTitle("Status!") // title for notification
//                .setContentText(message) // message for notification
//                .setAutoCancel(true); // clear notification after click
//        NotificationManager mNotificationManager =
//                (NotificationManager) instance.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(0, mBuilder.build());
//
//    }
//
//
//
//
//    @Override
//    public Context getApplicationContext() {
//        return null;
//    }
//
//    @Override
//    public void unbindService(ServiceConnection serviceConnection) {
//
//    }
//
//    @Override
//    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
//        return false;
//    }
//
//    private static void notifyListChange(){
//        if (instance.context != null && instance.onListRefreshListener != null) {
//            instance.context.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    BeagosApp.instance.onListRefreshListener.onListRefresh();
//                }
//            });
//        }
//    }

}
