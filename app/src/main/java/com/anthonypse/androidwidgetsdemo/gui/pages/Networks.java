package com.anthonypse.androidwidgetsdemo.gui.pages;

import com.anthonypse.androidwidgetsdemo.gui.BaseDemoPage;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;
import com.anthonypse.androidwidgetsdemo.gui.widgets.IntroDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonypse@gmail.com
 * This page will contain demos for networking apis like routing table editing
 */
public class Networks extends BaseDemoPage {
    @Override
    protected void setPageHeader() {
        mHeader = "Networks";
    }

    @Override
    protected List<DemoWidget> initDemoWidgets() {
        List<DemoWidget> returnMe = new ArrayList<>();
        //Add your demo widgets here
        returnMe.add(new IntroDemo());

        return returnMe;
    }
}
