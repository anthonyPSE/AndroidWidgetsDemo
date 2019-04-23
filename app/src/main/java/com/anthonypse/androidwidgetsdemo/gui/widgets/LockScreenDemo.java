package com.anthonypse.androidwidgetsdemo.gui.widgets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.anthonypse.androidutils.DeviceManager;
import com.anthonypse.androidutils.FileSelectionUtils;
import com.anthonypse.androidwidgetsdemo.R;
import com.anthonypse.androidwidgetsdemo.RequestCode;
import com.anthonypse.androidwidgetsdemo.database.PspecTable;
import com.anthonypse.androidwidgetsdemo.gui.DemoWidget;

import java.security.AccessControlException;

import static android.app.Activity.RESULT_OK;
import static com.anthonypse.androidutils.FileSelectionUtils.MimeContentType.IMG;

/**
 * Created by anthonypse@gmail.com
 * Allows you to change the look of the lock screen by selecting a custom image file from the device.
 */
public class LockScreenDemo extends DemoWidget {
    private static final String TAG = "LockScreenDemo";

    private DeviceManager mDevice;
    //TODO: Change this to wherever you keep your wallpapers.
    //TODO: Make sure you have proper permissions to access this location as well.
    private static final String WALLPAPER_DIRECTORY = Environment.getExternalStorageDirectory().getPath();// + Environment.DIRECTORY_PICTURES;

    protected Button mButtonSave;
    protected Button mButtonReset;
    protected Button mOpenFileDialog;
    protected ImageView mImgPreview;
    protected Uri mWallpaperUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getFrameWithController(inflater, container, R.layout.controller_lock_screen);
    }

    /**
     * Uses the pspec table database to get all the entries related to each keyword.
     */
    @Override
    protected void setReferences() {
        PspecTable database = new PspecTable(R.raw.pspec_reference, getContext());
        mSpecReferences.addAll(database.getEntry("LockScreen"));
    }

    @Override
    protected void bind(View v) {
        super.bind(v);
        mImgPreview = v.findViewById(R.id.imgPreview);
        mButtonSave = v.findViewById(R.id.btnSave);
        mButtonReset = v.findViewById(R.id.btnReset);
        mOpenFileDialog = v.findViewById(R.id.btnFileDialog);
    }

    @Override
    protected void init() {
        super.init();
        try {
            mDevice = new DeviceManager(getContext());
            mButtonSave.setEnabled(false);
            mButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveChanges();
                }
            });

            mButtonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reset();
                }
            });

            mOpenFileDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FileSelectionUtils.openFileDialog(LockScreenDemo.this, "Choose Wallpaper", Uri.parse(WALLPAPER_DIRECTORY), IMG, RequestCode.GET_WALLPAPER);
                }
            });

            mImgPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FileSelectionUtils.openFileDialog(LockScreenDemo.this, "Choose Wallpaper", Uri.parse(WALLPAPER_DIRECTORY), IMG, RequestCode.GET_WALLPAPER);
                }
            });

            mTextViewDescription.setText("Set Lock screen here");
        } catch (RuntimeException e) {
            e.printStackTrace();
            mInfo.showToast("Some elements of this page have not been loaded due to Tactical Edition library restrictions");
            mTextViewDescription.setText("Initialization error");
        }
    }

    /**
     * is called when returning from the file selector and the user has chosen an image file URI.
     * @param uri the URI of the image the user has chosen to be the lock screen's background.
     */
    private void onFileChosen(Uri uri) {
        Log.i(TAG, "onFileChosen");
        mButtonSave.setEnabled(true);
        mImgPreview.setImageURI(uri);
        mWallpaperUri = uri;
    }

    /**
     * Sets or resets the lock screen depending on the selected option.
     */
    protected void saveChanges() {
        if (mWallpaperUri == null) {
            mInfo.showToast("Something went wrong.  Did you select a wallpaper first?");
            return;
        }

        int result = mDevice.setLockScreenWallpaper(getPath(mWallpaperUri));
        if (result == 0) {//0 means no error codes
            mInfo.showToast("Lock screen set successfully");
        } else if (result == -2) {
            mInfo.showToast("You'll need to enable external read permissions in the app settings");
        } else {
            mInfo.showToast("Lock screen set failed.");
        }
    }

    protected void reset() {
        try {
            mDevice.resetLockScreenWallpaper();
            mImgPreview.setImageResource(R.color.transparent);
            mButtonSave.setEnabled(false);
            mInfo.showToast("You have reset the lock screen wallpaper to the factory default");
        } catch (AccessControlException e) {
            e.printStackTrace();
            mInfo.showSimpleDialog("Something weird happened", "Looks like you're not allowed to reset wallpaper.  Check admin privleges.  Also try disable/renable admin.");
        }
    }

    /**
     * this gets called after the file dialog has closed.
     *
     * @param requestCode user-set code which is ideally unique to this particular request
     * @param resultCode  indicates if the activity was cancelled or ended as intended.
     * @param data        contains the data returned from the called-upon activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");

        if (requestCode == RequestCode.GET_WALLPAPER) {
            Log.i(TAG, "Request Came Back: ");
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a file.
                // The Intent's data Uri identifies which file was selected.
                Uri uri;
                uri = data.getData();
                if (uri != null) {
                    mWallpaperUri = uri;
                    Log.i(TAG, "Uri: " + uri.toString());
                    onFileChosen(uri);
                }
            }
        }
    }

    /**
     * TODO: find out why this works lol
     *
     * @param uri
     * @return
     */
    protected String getPath(Uri uri) {
        String[] parts = uri.getPath().split(":");
        return Environment.getExternalStorageDirectory() + "/" + parts[1];
    }
}