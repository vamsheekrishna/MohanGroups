package com.example.mybusinesstracker.basecalss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class upDateNetworkStateReceiver extends BroadcastReceiver {

    private static final String UpdateNetwork_LOG_TAG= "NetworkStateReceiver";
    private boolean isConnected = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(UpdateNetwork_LOG_TAG,"Notification about Network");
        context.sendBroadcast(new Intent("NETWORK_STATE_CHANGE"));
    }
}
