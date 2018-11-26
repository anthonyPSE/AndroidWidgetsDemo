package com.anthonypse.androidwidgetsdemo.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import com.anthonypse.androidwidgetsdemo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Created by anthonypse@gmail.com
 */
public class PspecTable {
    private static final String TAG = "PspecTable";
    final private Map<String, Set<String>> mTable;
    final private Context mContext;
    public PspecTable(int rawCsvResourceId, @NonNull Context context){
        mContext = context;
        Map<String, Set<String>> temp = new HashMap<>();
        try {
            temp = convertResourceToTable(rawCsvResourceId);
        } catch( Resources.NotFoundException e ){
            Log.e(TAG, "Could not locate CSV.  Bad resource ID?");
            e.printStackTrace();
        }

        mTable = temp;
    }



    /**
     * Looks for a value with the associated key.  Returns empty list if none found.
     * @param key the unique key associated with the value you're looking for.
     * @return returns a list of strings
     */
    public Set<String> getEntry(String key){
        if(mTable == null )
            return new TreeSet<String>();
        if(mTable.containsKey(key)){
            return mTable.get(key);
        }
        return new TreeSet<String>();
    }

    private Map<String, Set<String>> convertResourceToTable(int rawCsvResourceId) throws Resources.NotFoundException {

        Map<String, Set<String>> returnMe = new HashMap<String, Set<String>>();
        InputStream is = mContext.getResources().openRawResource(R.raw.pspec_reference);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        try {
            String line;
            StringTokenizer st;
            while ((line = reader.readLine()) != null) {
                st = new StringTokenizer(line, ",");
                String key = st.nextToken();
                Set<String> refs = new TreeSet<>();

                //Loop until you run out of elements
                while(st.hasMoreTokens()){
                    String nextRef = st.nextToken();
                    if( !nextRef.isEmpty()){
                        refs.add(nextRef);
                    }
                }

                if(returnMe.containsKey(key)){
                    returnMe.get(key).addAll(refs);
                } else {
                    returnMe.put(key, refs);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnMe;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = mTable.entrySet().iterator();

        for (Map.Entry<String, Set<String>> entry : mTable.entrySet()) {
            sb.append("Key = ");
            sb.append(entry.getKey());
            sb.append(", Value = ");
            sb.append(entry.getValue());
            sb.append('\n');
        }
        return sb.toString();
    }
}
