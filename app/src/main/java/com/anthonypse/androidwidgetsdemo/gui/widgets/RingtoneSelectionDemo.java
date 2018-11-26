package com.anthonypse.androidwidgetsdemo.gui.widgets;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anthonypse.androidwidgetsdemo.Constants;
import com.anthonypse.androidwidgetsdemo.FileSelectionUtils;
import com.anthonypse.androidwidgetsdemo.MimeContentType;
import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.RequestCode;
import com.anthonypse.androidwidgetsdemo.database.PspecTable;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;

import static android.app.Activity.RESULT_OK;

/**
 * Created by anthonypse@gmail.com
 * This widget is intended to allow the user to open a file dialog, choose an MP3 file,
 * and set it as a ringtone
 *
 * This requires elevated app permissions which are not possible on official rom distros.
 */
public class RingtoneSelectionDemo extends DemoWidget {
    private static final String TAG = "RingtoneSelectionDemo";

    Uri mRingtoneUri;
    TextView mFileName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return getFrameWithController(inflater, container, R.layout.controller_ringtone);
    }

    @Override
    protected void setReferences() {
        PspecTable database = new PspecTable(R.raw.pspec_reference, getContext());
        mSpecReferences.addAll(database.getEntry("RingTone"));
    }

    @Override
    protected void init(){
        super.init();
        mTextViewDescription.setText("Set your ringtone here");
    }
    @Override
    protected void bind(View v) {
        super.bind(v);
        View controls = v.findViewById(R.id.control_panel);
        mFileName = controls.findViewById(R.id.textSelectedFile);
        controls.findViewById(R.id.buttonOpenFileSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileSelectionUtils.openFileDialog(RingtoneSelectionDemo.this, "Choose Ringtone", Uri.parse(Constants.SD_CARD_DIRECTORY), MimeContentType.AUDIO, RequestCode.GET_RINGTONE);
            }
        });

        controls.findViewById(R.id.buttonSaveChanges).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onSaveRingtone(v);
            }
        });
    }

    /**
     * this gets called after the file dialog has closed.
     *
     * @param requestCode user-set code which is ideally unique to this particular request
     * @param resultCode indicates if the activity was cancelled or ended as intended.
     * @param data contains the data returned from the called-upon activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.GET_RINGTONE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a file.
                // The Intent's data Uri identifies which file was selected.
                Uri uri;
                uri = data.getData();
                if (uri != null) {
                    mRingtoneUri = uri;
                    Log.i(TAG, "Uri: " + uri.toString());
                    //showImage(uri);
                    mFileName.setText(FileSelectionUtils.getFileName(getContext(), uri));
                }
            }
        }
    }

    /**
     * Resets the display text and saves the ringtone
     * @param v
     */
    public void onSaveRingtone(View v){
        setRingtone(mRingtoneUri);
        mFileName.setText("");
        mRingtoneUri = null;
    }

    /**
     * Sets the device ringtone
     * @param uri the uri for the ringtone you wish to set.
     */
    private void setRingtone(Uri uri) {
        try {
            if(uri == null)
                throw new NullPointerException("uri is null.  Did you select a file?");
            RingtoneManager.setActualDefaultRingtoneUri(
                    getActivity(),
                    RingtoneManager.TYPE_RINGTONE,
                    uri
            );
            Toast.makeText(getContext(), "Ringtone Set", Toast.LENGTH_LONG).show();

        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Your OS security features have blocked this action.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "An error occurred when trying to set your ringtone.", Toast.LENGTH_LONG).show();
        }
    }
}
