/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util.file;

import liquibase.util.FilenameUtil;

@Deprecated
public class FilenameUtils
extends FilenameUtil {
    @Deprecated
    public static String getFullPath(String filename) {
        return FilenameUtils.getDirectory(filename);
    }
}

