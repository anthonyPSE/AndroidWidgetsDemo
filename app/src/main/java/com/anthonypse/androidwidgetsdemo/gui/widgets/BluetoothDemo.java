package com.anthonypse.androidwidgetsdemo.gui.widgets;

import com.anthonypse.androidutils.NetworkManager;
import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.database.PspecTable;

/**
 * Created by anthonypse@gmail.com
 * This widget can toggle bluetooth on and off.
 */
public class BluetoothDemo extends SettingsToggleDemo {
    private static final String TAG = "BluetoothDemo";
    private NetworkManager mNetworkManager;

    @Override
    protected void setReferences() {
        PspecTable database = new PspecTable(R.raw.pspec_reference, getContext());
        mSpecReferences.addAll(database.getEntry("BluetoothAntenna"));
    }

    @Override
    protected void init() {
        mNetworkManager = new NetworkManager(getContext());
        mTextViewDescription.setText("Toggle Bluetooth antenna");
        super.init();
        mToggle.setText("Bluetooth: ");
    }

    @Override
    protected void onToggle() {
        try {
            boolean isChanged = mToggle.isChecked() != mNetworkManager.isBlueToothEnabled();
            mSave.setEnabled(isChanged);
        } catch (NullPointerException e) {
            mInfo.logError("Bluetooth adjustment unavailable");
            disableButtons();
        }
    }

    @Override
    protected void onSave() {
        try {
            boolean status = mNetworkManager.setBluetoothEnabled(mToggle.isChecked());
            if (!status) {
                mInfo.showToast("Unable to save changes");
            } else {
                mInfo.showToast("Bluetooth enabled: " + String.valueOf(mToggle.isChecked()));
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
            return mNetworkManager.isBlueToothEnabled();
        } catch (SecurityException e) {
            e.printStackTrace();
            mToggle.setEnabled(false);
        } catch (NullPointerException e) {
            mInfo.logError("Bluetooth adapter is null.");
            mTextViewDescription.setText("No bluetooth device detected");
            disableButtons();
        }
        return false;
    }
}
