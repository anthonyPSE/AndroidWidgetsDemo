package com.anthonypse.androidwidgetsdemo.gui;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anthonypse.androidutils.InfoUtil;
import com.anthonypse.androidwidgetsdemo.R;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 */
public abstract class DemoWidget extends Fragment {
    private static final String TAG = "DemoItemFragment";

    protected Set<String> mSpecReferences = new TreeSet<String>();
    protected TextView mTextViewDescription;
    protected InfoUtil mInfo;
    private Button mInfoButton;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.demo_frame, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        bind(view);
        init();
    }

    /**
     * This one's a little tricky to explain so here goes:
     * All demo widgets have a similar layout, a frame with a control panel.
     * Instead of doing a bunch of impossible magic with recycler views and fragment transactions
     * I thought it might be a good idea to practice programmatically swapping out views in the
     * onCreateView function.
     *
     * In summary, this function will take a demo frame, rip out the dummy control panel view
     * and replace it with the actual control panel.
     * The child of this class is responsible for supplying the actual control panel to be used.
     *
     * The end result sees a usable control panel with the same layout params and id as the dummy,
     * all of which sits inside a standard demo widget frame.
     * @param inflater the invoking object's inflater.
     * @param container the container from the invoker's onCreateView call
     * @param replacementControllerId the control panel unique to the child DemoWidget
     * @return a ViewGroup that houses a the new control panel instead of the dummy one.
     */
    protected View getFrameWithController(LayoutInflater inflater, ViewGroup container, int replacementControllerId){
        Log.i(TAG, "getFrameWithController");

        ViewGroup demoFrame = (ViewGroup)inflater.inflate(R.layout.demo_frame, container, false);
        View placeholderControlPanel = demoFrame.findViewById(R.id.control_panel);
        View replacementControlPanel = inflater.inflate(replacementControllerId, null, false);

        //Remove the old control panel
        demoFrame.removeView(placeholderControlPanel);

        //Give the new control panel the id and layout parameters as the old one
        replacementControlPanel.setId(R.id.control_panel);
        replacementControlPanel.setLayoutParams(placeholderControlPanel.getLayoutParams());

        //Add the new control panel to the view group.  You've now effectively swapped
        //the panels
        demoFrame.addView(replacementControlPanel);
        return demoFrame;
    }

    /**
     * Override this in derived classes.
     * Use this function to set this class's pspec references.
     */
    protected void setReferences(){
        Log.i(TAG, "setReferences");

        mSpecReferences = new TreeSet<>();
        mSpecReferences.add("Data missing");
    }

    protected void showRefsDialog() {
        Log.i(TAG, "showRefsDialog");
        String message = "Data missing";
        if (mSpecReferences != null && mSpecReferences.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            for (String mSpecReference : mSpecReferences) {
                stringBuilder.append(mSpecReference);
                stringBuilder.append('\n');
            }
            message = stringBuilder.toString();
        }
        mInfo.showSimpleDialog("Header", message);
    }

    /**
     * Sends a message to device log at info level;
     * @param message message for the log.
     */
    protected void infoLog(String message){
        Log.i(TAG, message);
    }

    protected void showSecurityDialog(){
        Log.i(TAG, "showSecurityDialog");
        mInfo.showSimpleDialog("Security header", "Security message");
    }

    /**
     * Call your "findViewById" stuff here and bind them to your view data members.
     * @param v the root view of this widget
     */
    @CallSuper
    protected void bind(View v){
        mTextViewDescription = (TextView) v.findViewById(R.id.description);
        mInfoButton = v.findViewById(R.id.more_info);

    }

    /**
     * Initialize non-view data members
     * set views beginning state (initial text, switch on/off, etc)
     */
    @CallSuper
    protected void init(){
        setReferences();
        mInfo = new InfoUtil(getContext(), getClass().getSimpleName());
        mInfo.logInfo("InfoUtil initialized. Your class name should appear before this string");
        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRefsDialog();
            }
        });
    }

}
