/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.File;

public class StoreUtil {
    public static File findParentDirectory(File dir) {
        if (dir != null) {
            String dirPath = dir.getAbsolutePath();
            if (!dir.isAbsolute()) {
                dir = new File(dirPath);
            }
            while (dir != null && !dir.isDirectory()) {
                dir = dir.getParentFile();
            }
        }
        return dir;
    }
}

