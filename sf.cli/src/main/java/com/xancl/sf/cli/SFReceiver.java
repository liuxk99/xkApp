package com.xancl.sf.cli;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class SFReceiver extends BroadcastReceiver {
    private static final String TAG = SFReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive(" + context + ", " + intent + ")");

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String a = intent.getAction();
        assert a != null;
        if (a.equals(XancL.ACTION_SHARE_FILE)) {
        }
    }
}
