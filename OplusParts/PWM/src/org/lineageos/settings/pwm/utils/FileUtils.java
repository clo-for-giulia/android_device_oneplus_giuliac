package org.lineageos.settings.pwm.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class FileUtils {
    private static final String TAG = "OplusPartsPWMFileUtils";

    private FileUtils() {}

    public static boolean isFileReadable(String fileName) {
        if (fileName == null) return false;
        final File file = new File(fileName);
        return file.exists() && file.canRead();
    }

    public static boolean isFileWritable(String fileName) {
        if (fileName == null) return false;
        final File file = new File(fileName);
        return file.exists() && file.canWrite();
    }

    public static String readOneLine(String fileName) {
        if (!isFileReadable(fileName)) return null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName), 512)) {
            return br.readLine();
        } catch (IOException e) {
            Log.e(TAG, "Failed to read line from " + fileName, e);
            return null;
        }
    }

    public static boolean writeLine(String fileName, String value) {
        if (!isFileWritable(fileName)) return false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(value);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Failed to write line to " + fileName, e);
            return false;
        }
    }
}
