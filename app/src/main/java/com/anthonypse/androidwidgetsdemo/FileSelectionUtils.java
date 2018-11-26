package com.anthonypse.androidwidgetsdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * tools to make it easier to open the file selector and get file names and stuff.
 * Created by anthonypse@gmail.com
 */
public class FileSelectionUtils {
    public static final String TAG = "FileSelectionUtils";
    //Request codes for convenience

    public static String getFileName(Context context, Uri uri) {
        Log.i(TAG, "getFileName");

        if (uri == null)
            return "";

        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static void openFileDialog(Fragment parentFragment, @NonNull String dialogMsg, @NonNull Uri folder, @NonNull String fileType, int requestCode) {
        Log.i(TAG, "openFileDialog");

        if (!fileType.isEmpty()) {
            Intent intent = new Intent()
                    .setAction(Intent.ACTION_GET_CONTENT);
            intent = intent.setDataAndType(folder,fileType);

            parentFragment.startActivityForResult(Intent.createChooser(intent, dialogMsg), requestCode);
        }
    }
}
