package com.anthonypse.androidwidgetsdemo.gui.pages;

import com.anthonypse.androidwidgetsdemo.gui.BaseDemoPage;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;
import com.anthonypse.androidwidgetsdemo.gui.widgets.BluetoothDemo;
import com.anthonypse.androidwidgetsdemo.gui.widgets.WifiDemo;
import com.anthonypse.androidwidgetsdemo.gui.widgets.VibrateDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonypse@gmail.com
 *
 * As with all BaseDemoPages this is one of the pages that are navigable from the navdrawer.
 * This page allows the user to change various settings with internal devices bluetooth,
 * wifi, and cell antennas
 */
public class InternalHardware extends BaseDemoPage {

    @Override
    protected void setPageHeader() {
        mHeader = "Internal Devices";
    }

    @Override
    protected List<DemoWidget> initDemoWidgets(){
        List<DemoWidget> returnMe = new ArrayList<>();
        //Add your demo widgets here
        returnMe.add(new WifiDemo());
        returnMe.add(new BluetoothDemo());
        returnMe.add(new VibrateDemo());
        return returnMe;
    }
}
