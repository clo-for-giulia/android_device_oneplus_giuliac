package org.lineageos.settings.pwm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.lineageos.settings.pwm.dc.DcDimmingHelper;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = "OplusPartsPWMBoot";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        Log.d(TAG, "Received boot intent: " + intent.getAction());
        DcDimmingHelper.restoreState(context);
    }
}
