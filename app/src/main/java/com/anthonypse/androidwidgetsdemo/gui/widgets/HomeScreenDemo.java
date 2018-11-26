package com.anthonypse.androidwidgetsdemo.gui.widgets;


import com.anthonypse.androidutils.DeviceManager;
import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.database.PspecTable;

/**
 * Created by anthonypse@gmail.com
 * This demo is for changing wallpaper.
 */
public class HomeScreenDemo extends LockScreenDemo {
    private static final String TAG = "HomeScreenDemo";
    private DeviceManager mDeviceManager;

    @Override
    protected void setReferences() {
        PspecTable database = new PspecTable(R.raw.pspec_reference, getContext());
        mSpecReferences.addAll(database.getEntry("HomeScreen"));
    }

    @Override
    protected void init() {
        super.init();
        mDeviceManager = new DeviceManager(getContext());
        mTextViewDescription.setText("Set Home Screen Wallpaper");
    }

    /**
     * Either enables or disables all the buttons on this widget.
     *
     * @param enable the enabled state for the buttons.
     */
    private void enableControllerButtons(boolean enable) {
        mButtonReset.setEnabled(enable);
        mButtonSave.setEnabled(enable);
        mOpenFileDialog.setEnabled(enable);
        mImgPreview.setEnabled(enable);
    }

    /**
     * Sets or resets the lock screen depending on the selected option.
     */
    @Override
    protected void saveChanges() {
        if (mWallpaperUri == null) {
            mInfo.showToast("Something went wrong.  Did you select a wallpaper first?");
            return;
        }

        boolean result = mDeviceManager.setHomeScreenWallpaper(mWallpaperUri);
        if (result) mInfo.showToast("System wallpaper set successfully");
        else mInfo.showToast("System wallpaper not set");

    }

    @Override
    protected void reset() {
        if (mDeviceManager.resetHomeScreenWallpaper()) {
            mInfo.showToast("System wallpaper reset successfully");
        } else {
            mInfo.showToast("System wallpaper reset failed");
        }
    }
}
