package com.example.budakgigibesi.ibeacon_scan;

import android.app.Activity;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

//public class MainActivity extends AppCompatActivity implements BeaconConsumer {
public class MainActivity extends Activity implements BeaconConsumer {

    //private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
	private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);		
		
		//enable beacon features///////////////////////////////////////////////////////////////////////

		beaconManager = BeaconManager.getInstanceForApplication(this);
        //BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
		beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
		
		beaconManager.bind(this);
		
		//////////////////////////////////////////////////////////////////////////////////////////////
    }
	
	@Override
    public void onBeaconServiceConnect() {
        //beaconManager.setRangeNotifier(new RangeNotifier() {
		beaconManager.addRangeNotifier(new RangeNotifier() {
           @Override
           public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
              if (beacons.size() > 0) {
                Log.i("beaconconsumer", "didRangeBeaconsInRegion called with beacon count:  "+beacons.size());
                Beacon firstBeacon = beacons.iterator().next();
                Log.i("beaconconsumer", "The first beacon " + firstBeacon.toString() + " is about " + firstBeacon.getDistance() + " meters away.");
				TextView mTextView = (TextView) findViewById(R.id.id_tv);
				mTextView.setText( "The first beacon " + firstBeacon.toString() + " is about " + firstBeacon.getDistance() + " meters away.");
              }
           }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }
	
	
}
