package com.espeedboat.admin.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;

public class FileUtils {
    public static File getFile(Context context, Uri uri) {
        if (uri != null) {
            String path = uri.getPath();
            if (path != null) {
                return new File(path);
            }
        }
        return null;
    }
}

