package com.anthonypse.androidwidgetsdemo.gui.pages;

import com.anthonypse.androidwidgetsdemo.gui.BaseDemoPage;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;
import com.anthonypse.androidwidgetsdemo.gui.widgets.HomeScreenDemo;
import com.anthonypse.androidwidgetsdemo.gui.widgets.LockScreenDemo;
import com.anthonypse.androidwidgetsdemo.gui.widgets.RingtoneSelectionDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * As with all BaseDemoPages this is one of the pages that are navigable from the navdrawer.
 * This page allows the user to change various settings such as ringtone and charging options.
 */
public class DeviceSettings extends BaseDemoPage {
    private static final String TAG = "DeviceSettings";

    @Override
    protected void setPageHeader() {
        mHeader = "Device Settings";
    }

    protected List<DemoWidget> initDemoWidgets(){
        List<DemoWidget> returnMe = new ArrayList<>();
        //Add your demo widgets here
        returnMe.add(new LockScreenDemo());
        returnMe.add(new HomeScreenDemo());
        returnMe.add(new RingtoneSelectionDemo());
        return returnMe;
    }
}
