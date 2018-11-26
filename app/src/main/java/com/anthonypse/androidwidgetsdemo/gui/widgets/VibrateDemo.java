package com.anthonypse.androidwidgetsdemo.gui.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anthonypse.androidutils.DeviceManager;
import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.database.PspecTable;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;

import java.util.TreeSet;

/**
 * Created by anthonypse@gmail.com
 * Allows the user to control the phone's vibrate motor.
 */
public final class VibrateDemo extends DemoWidget {

    protected DeviceManager mDeviceManager;
    protected Button mVibrate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return getFrameWithController(inflater, container, R.layout.controller_vibration);
    }

    @Override
    protected void setReferences() {
        PspecTable database = new PspecTable(R.raw.pspec_reference, getContext());
        mSpecReferences.addAll(database.getEntry("Vibration"));
    }

    @Override
    protected void bind(View v){
        super.bind(v);
        mVibrate = v.findViewById(R.id.buttonVibrate);
    }

    @Override
    protected void init(){
        super.init();
        mDeviceManager = new DeviceManager(getContext());
        mTextViewDescription.setText("Control vibration");
        mVibrate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onPressed();
            }
        });
    }

    /**
     * this is what happens when you press the vibrate button
     */
    protected void onPressed(){
        try {
            mDeviceManager.vibrateDevice(1000);
        } catch (Exception e){
            e.printStackTrace();
            mInfo.showToast("Vibration not supported");
        }
    }
}
