package com.anthonypse.androidwidgetsdemo.gui.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;

/**
 * Created by anthonypse@gmail.com
 *
 * There are going to be a lot of widgets that simply turn a single setting on or off.
 * This class makes it easy to write such a widget that you need only to write minimal
 */
public abstract class SettingsToggleDemo extends DemoWidget implements Switch.OnCheckedChangeListener, View.OnClickListener {
    private static final String TAG = "SettingsToggleDemo";

    protected Switch mToggle;
    protected Button mSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return getFrameWithController(inflater, container, R.layout.controller_simple_toggle);
    }

    @Override
    protected void bind(View v){
        super.bind(v);
        mToggle = v.findViewById(R.id.toggleSetting);
        mSave = v.findViewById(R.id.buttonSaveChanges);
    }

    @Override
    protected void init(){
        super.init();
        mSave.setEnabled(false);
        mSave.setOnClickListener(this);
        mToggle.setChecked(getInitialState());
        mToggle.setOnCheckedChangeListener(this);
    }

    /**
     * This is intended to be called during initialization if a critical module is unable
     * to be initialized.  For example, if you're running on a non-tactical device and you try to
     * instantiate knox.
     */
    protected void disableButtons(){
        mToggle.setEnabled(false);
        mSave.setEnabled(false);
    }
    /**
     * This is what happens when the switch changes position.
     * NB: it's bad design to have the switch actually save the setting on change.
     * That functionality should be reserved for the save button.
     */
    protected abstract void onToggle();

    /**
     * Handle saving the settings change here.  Make sure you show success and failure as appropriate
     */
    protected abstract void onSave();

    /**
     * This will set the toggle switch to whatever the current state of the setting is.
     * @return
     */
    protected abstract boolean  getInitialState();

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        onToggle();
    }

    @Override
    public void onClick(View v) {
        onSave();
    }
}
