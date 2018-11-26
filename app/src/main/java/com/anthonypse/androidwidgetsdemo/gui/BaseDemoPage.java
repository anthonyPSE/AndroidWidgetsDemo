package com.anthonypse.androidwidgetsdemo.gui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonypse@gmail.com
 * <p>
 * Base class for all pages which will be filled with demo widgets
 */
public abstract class BaseDemoPage extends Fragment {
    private static final String TAG = "BaseDemoPage";
    protected String mHeader;
    private List<String> mDemoItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDemoItems = new ArrayList<String>();
        return inflater.inflate(R.layout.fragment_blank_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addDemoWidgets(initDemoWidgets());
        setPageHeader();
        initPageHeader();
    }

    /**
     * Override this if you wish for this page to have a different header text.
     */
    protected void setPageHeader() {
        mHeader = "Widgets Demo";
    }

    /**
     * Sets the action bar's title.
     */
    private void initPageHeader() {
        AppCompatActivity parentActivity;
        if (getActivity() instanceof AppCompatActivity) {
            parentActivity = (AppCompatActivity) getActivity();
            ActionBar actionBar = parentActivity.getSupportActionBar();

            if (actionBar != null)
                parentActivity.getSupportActionBar().setTitle(mHeader);
        }
    }

    /**
     * Child classes must define a list of demo widgets
     *
     * @return a list of the Demo Widgets that will be displayed on this page.
     */
    abstract protected List<DemoWidget> initDemoWidgets();

    /**
     * Adds all the widgets from the list parameter to this page's layout
     *
     * @param widgetList a list of the Demo Widgets that will be displayed on this page.
     */
    final protected void addDemoWidgets(List<DemoWidget> widgetList) {
        Log.i(TAG, "addDemoWidgets");
        if (widgetList == null)
            return;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mDemoItems.clear();
        for (DemoWidget w : widgetList) {
            //if(fm.findFragmentByTag(w.getClass().getName()) == null){
                ft.add(R.id.fragment_list, w, w.getClass().getName());
                mDemoItems.add(w.getClass().getName());
            //}
        }
        ft.commit();
    }
}