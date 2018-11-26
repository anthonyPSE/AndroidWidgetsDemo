package com.anthonypse.androidwidgetsdemo.gui.widgets;

import com.anthonypse.androidutils.NetworkManager;
import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.database.PspecTable;

/**
 * Created by anthonypse@gmail.com
 * Here's a widget that lets you toggle wifi on/off.
 */
public class WifiDemo extends SettingsToggleDemo{
    private static final String TAG = "WifiDemo";
    private NetworkManager mNetworkManager;
    @Override
    protected void setReferences() {
        PspecTable database = new PspecTable(R.raw.pspec_reference, getContext());
        mSpecReferences.addAll(database.getEntry("WifiAntenna"));
    }

    @Override
    protected void init() {
        mNetworkManager = new NetworkManager(getContext());
        super.init();
        mTextViewDescription.setText("Toggle Wifi antenna");
        mToggle.setText("Wi-Fi: ");

    }

    @Override
    protected void onToggle() {
        boolean isChanged = mToggle.isChecked() != mNetworkManager.isWifiEnabled();
        mSave.setEnabled(isChanged);
    }

    @Override
    protected void onSave() {
        try {
            boolean status = mNetworkManager.setWifiEnabled(mToggle.isChecked());
            if (!status) {
                mInfo.showToast("Unable to save changes");
            } else {
                mInfo.showToast("Wifi enabled: " + String.valueOf(mToggle.isChecked()));
                mSave.setEnabled(false);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            showSecurityDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean getInitialState() {
        try {
            return mNetworkManager.isWifiEnabled();
        } catch (SecurityException e) {
            e.printStackTrace();
            mToggle.setEnabled(false);
        }
        return false;
    }
}
