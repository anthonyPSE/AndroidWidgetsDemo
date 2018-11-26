package com.anthonypse.androidwidgetsdemo.gui.widgets;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anthonypse.androidwidgetsdemo.ColorInputFilter;
import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;

/**
 * Created by anthonypse@gmail.com
 */
public class BannerDemo extends DemoWidget implements TextWatcher, View.OnFocusChangeListener, View.OnClickListener {
    TextView mDisplay;
    EditText mColorR;
    EditText mColorG;
    EditText mColorB;
    EditText mBanner;
    Button mSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return getFrameWithController(inflater, container, R.layout.controller_banner);
    }

    @Override
    protected void bind(View v) {
        super.bind(v);
        mBanner = v.findViewById(R.id.editBanner);
        mDisplay = v.findViewById(R.id.textColor);
        mColorR = v.findViewById(R.id.editColorR);
        mColorG = v.findViewById(R.id.editColorG);
        mColorB = v.findViewById(R.id.editColorB);
        mSave = v.findViewById(R.id.buttonSave);
    }

    @Override
    protected void init() {
        super.init();
        mSave.setOnClickListener(this);

        //Filter makes sure the input values range from 0-255
        mColorR.setFilters(new InputFilter[]{new ColorInputFilter(0, 255)});
        mColorG.setFilters(new InputFilter[]{new ColorInputFilter(0, 255)});
        mColorB.setFilters(new InputFilter[]{new ColorInputFilter(0, 255)});

        //Change listener gives live updates to the color display
        mColorR.addTextChangedListener(this);
        mColorG.addTextChangedListener(this);
        mColorB.addTextChangedListener(this);

        //Focus listener makes sure you don't leave a blank value when changing focus
        mColorR.setOnFocusChangeListener(this);
        mColorG.setOnFocusChangeListener(this);
        mColorB.setOnFocusChangeListener(this);

        updateColorDisplay();
    }

    /**
     * Returns the opposite color value.
     * This is useful when you want text to show up on a certain background.
     * Examples:    converts green to magenta
     *              converts black to white
     *              converts red to cyan
     *              etc.
     * @param color the color you wish to invert.
     * @return  the inverse color int in #0xAARRGGBB format.
     *          Ignores alpha; always returns max opacity
     */
    private int getInverseColor(int color){
        int r = 255 - Color.red(color);
        int g = 255 - Color.green(color);
        int b = 255 - Color.blue(color);
        return Color.argb(255, r, g, b);
    }

    /**
     * Gets the values of all the EditTexts, constructs a color
     */
    private int getUserColor() {
        //Get the int values of the color editors
        int r = getValidatedIntColorValue(mColorR);
        int g = getValidatedIntColorValue(mColorG);
        int b = getValidatedIntColorValue(mColorB);

        return Color.argb(255, r, g, b); // from a color int
    }

    /**
     * returns the int equivalent value of the given EditText's string.
     * @param et the EditText you wish to retrieve a value from
     * @return the int value, zero if the string is invalid.
     */
    private int getValidatedIntColorValue(EditText et){
        try {
            return Integer.parseInt(et.getText().toString());
        }catch(NumberFormatException e){//This is one case where a catch-all is acceptable.
            return 0;
        }
    }

    /**
     * updates the color display to match the values displayed in the text boxes.
     */
    private void updateColorDisplay(){
        int colorARGB = getUserColor();
        mDisplay.setBackgroundColor(colorARGB);
        mDisplay.setTextColor(getInverseColor(colorARGB));
    }

    private void saveAsBanner(){
        int color = getUserColor();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(color);
        } else {
            mInfo.showToast("Minimum API level 21 is required.");
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //Do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updateColorDisplay();
    }

    @Override
    public void afterTextChanged(Editable s) {
        //Do nothing
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!(v instanceof EditText) ){
            return;
        }

        EditText colorValue = (EditText)v;
        if(colorValue.getText().toString().isEmpty()){
            colorValue.setText("0");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonSave){
            saveAsBanner();
        }
    }
}
