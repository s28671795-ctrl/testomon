/*
 * Decompiled with CFR 0.152.
 */
package javax.activation;

import java.io.File;
import javax.activation.MimetypesFileTypeMap;

public abstract class FileTypeMap {
    private static FileTypeMap defaultMap = null;

    public abstract String getContentType(File var1);

    public abstract String getContentType(String var1);

    public static void setDefaultFileTypeMap(FileTypeMap map) {
        block3: {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                try {
                    security.checkSetFactory();
                }
                catch (SecurityException ex) {
                    if (FileTypeMap.class.getClassLoader() == map.getClass().getClassLoader()) break block3;
                    throw ex;
                }
            }
        }
        defaultMap = map;
    }

    public static FileTypeMap getDefaultFileTypeMap() {
        if (defaultMap == null) {
            defaultMap = new MimetypesFileTypeMap();
        }
        return defaultMap;
    }
}

