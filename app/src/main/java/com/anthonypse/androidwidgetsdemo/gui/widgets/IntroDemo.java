package com.anthonypse.androidwidgetsdemo.gui.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.database.PspecTable;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;

/**
 * Created by anthonypse@gmail.com
 * Shows the user an introduction to the SDK demo app.
 */
public class IntroDemo extends DemoWidget {
    private static final String TAG = "IntroDemo";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return getFrameWithController(inflater, container, R.layout.controller_intro);
    }
    @Override
    protected void setReferences() {
        PspecTable database = new PspecTable(R.raw.pspec_reference, getContext());
        mSpecReferences.addAll(database.getEntry("Intro"));
    }

    @Override
    protected void init() {
        Log.i(TAG, "init");
        super.init();
        mTextViewDescription.setText("Welcome to the MCH SDK Demo!");

    }
}
