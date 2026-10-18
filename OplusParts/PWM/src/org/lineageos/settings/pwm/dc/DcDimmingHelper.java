package org.lineageos.settings.pwm.dc;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import org.lineageos.settings.pwm.utils.FileUtils;

public final class DcDimmingHelper {
    private static final String TAG = "DcDimmingHelper";

    public static final String KEY_DC_DIMMING = "dc_dimming_enable";
    public static final String DC_SYSFS_PATH = "/sys/kernel/oplus_display/pwm_onepulse";

    private DcDimmingHelper() {}

    public static boolean isSupported() {
        return FileUtils.isFileReadable(DC_SYSFS_PATH)
                && FileUtils.isFileWritable(DC_SYSFS_PATH);
    }

    public static boolean isCurrentlyEnabled() {
        String value = FileUtils.readOneLine(DC_SYSFS_PATH);
        return value != null && "1".equals(value.trim());
    }

    public static boolean isEnabled(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(KEY_DC_DIMMING, isCurrentlyEnabled());
    }

    public static boolean setEnabled(Context context, boolean enabled) {
        if (!isSupported()) return false;
        boolean success = FileUtils.writeLine(DC_SYSFS_PATH, enabled ? "1" : "0");
        if (success) {
            PreferenceManager.getDefaultSharedPreferences(context)
                    .edit()
                    .putBoolean(KEY_DC_DIMMING, enabled)
                    .apply();
            Log.i(TAG, "Set pwm_onepulse to " + (enabled ? "1" : "0"));
        }
        return success;
    }

    public static void restoreState(Context context) {
        if (!isSupported()) return;
        boolean savedState = isEnabled(context);
        FileUtils.writeLine(DC_SYSFS_PATH, savedState ? "1" : "0");
        Log.i(TAG, "Restored pwm_onepulse state: " + savedState);
    }
}
