package com.bosan.audiorecordbybluetooth.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bosan.audiorecordbybluetooth.MainActivity2;

/**
 * 监听启动广播
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_BOOT_COMPLETED)) {
            Intent activityIntent = new Intent(context, MainActivity2.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);
        }
    }

}
