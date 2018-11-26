package com.anthonypse.androidutils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;

public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private Context mApplicationContext;

    public NetworkManager(Context context) {
        mApplicationContext = context;

    }

    public boolean isWifiEnabled(){
        WifiManager manager = (WifiManager) mApplicationContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (manager != null) {
            return manager.isWifiEnabled();
        }
        return false;
    }
    public boolean isBlueToothEnabled(){
        if( BluetoothAdapter.getDefaultAdapter() != null ){
            return BluetoothAdapter.getDefaultAdapter().isEnabled();
        } else {
            throw new NullPointerException();
        }
    }

    public boolean setWifiEnabled(boolean isEnabled){
        WifiManager manager = (WifiManager) mApplicationContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        try {
            if(manager == null)
                return false;
            return manager.setWifiEnabled(isEnabled);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setBluetoothEnabled(boolean isEnabled){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        try {
            if (isEnabled) {
                return adapter.enable();
            } else {
                return adapter.disable();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAirplaneModeEnabled() {
        return Settings.System.getInt(mApplicationContext.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }
}
