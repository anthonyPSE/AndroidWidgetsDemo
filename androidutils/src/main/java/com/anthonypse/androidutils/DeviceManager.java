package com.anthonypse.androidutils;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;

import java.io.IOException;

import static android.content.Context.VIBRATOR_SERVICE;

public class DeviceManager {
    private Context mContext;
    InfoUtil mInfo;
    public DeviceManager(Context context) {
        mContext = context;
        mInfo = new InfoUtil(mContext);
    }

    public int setLockScreenWallpaper(String filePath){
        return -1;
    }

    public void resetLockScreenWallpaper(){

    }
    public boolean resetHomeScreenWallpaper(){
        WallpaperManager manager = WallpaperManager.getInstance(mContext);

        try {
            manager.clear();
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     *
     * @param uri
     * @return
     */
    public boolean setHomeScreenWallpaper(Uri uri){
        WallpaperManager manager = WallpaperManager.getInstance(mContext);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            manager.setBitmap(bitmap);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setSystemWallpaper(int resourceId){
        WallpaperManager manager = WallpaperManager.getInstance(mContext);

        try {
            manager.setResource(resourceId);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isWallpaperChangeAllowed(){
        return false;
    }

    public void vibrateDevice(long milliseconds) throws Exception {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(VIBRATOR_SERVICE);

        if (vibrator == null) throw new Exception("Vibrator could not be retrieved.");

        if (!vibrator.hasVibrator()) throw new Exception("No vibration motor detected");

        vibrator.vibrate(milliseconds);
    }
}
