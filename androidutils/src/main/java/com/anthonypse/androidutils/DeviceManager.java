package com.anthonypse.androidutils;

import android.content.Context;
import android.net.Uri;

public class DeviceManager {
    private Context mContext;

    public DeviceManager(Context context) {
        mContext = context;
    }

    public int setLockScreenWallpaper(String filePath){
        return -1;
    }

    public void resetLockScreenWallpaper(){

    }
    public boolean resetHomeScreenWallpaper(){
        return false;
    }
    public boolean setHomeScreenWallpaper(Uri uri){
        return false;
    }

    public boolean isWallpaperChangeAllowed(){
        return false;
    }

    public void vibrateDevice(long milliseconds){

    }
}
