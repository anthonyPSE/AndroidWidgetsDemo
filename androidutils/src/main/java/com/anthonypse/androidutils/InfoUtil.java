package com.anthonypse.androidutils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

public class InfoUtil {
    private static final String TAG = "InfoUtil";
    private Context mContext;
    private final String mClassName;

    public InfoUtil(Context context, String className) {
        mContext = context;
        mClassName = className;
    }

    public InfoUtil(Context context) {
        mContext = context;
        mClassName = "";
    }

    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public void showSimpleDialog(String header, String message) {
        Log.i(TAG, "showTextDialog");

        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message)
                .setTitle(header);

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void logInfo(String message) {
        Log.i(TAG, appendClassNameToMessage(message));
    }

    public void logWarning(String message) {
        Log.w(TAG, appendClassNameToMessage(message));
    }

    public void logError(String message) {
        Log.e(TAG, appendClassNameToMessage(message));
    }

    public void logDebug(String message) {
        Log.d(TAG, appendClassNameToMessage(message));
    }

    /**
     * Appends the controlling class name to the message so it can more easily be searched in the log.
     *
     * @param message a message which is usually printed to the log.
     * @return returns a string that is whatever the class name is plus a scope operator, plus the message.
     * It's supposed to look kinda like a C++ style function call
     */
    private String appendClassNameToMessage(String message) {
        if (mClassName == null)
            return message;
        return mClassName + "::" + message;
    }
}
