package com.anthonypse.androidutils;

import android.content.Context;

public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private Context mContext;

    public NetworkManager(Context context) {
        mContext = context;

    }

    public boolean isWifiEnabled(){
        return false;
    }
    public boolean isBlueToothEnabled(){
        return false;
    }

    public boolean setWifiEnabled(boolean enabled){
        return false;
    }

    public boolean setBluetoothEnabled(boolean enabled){
        return false;
    }
}
