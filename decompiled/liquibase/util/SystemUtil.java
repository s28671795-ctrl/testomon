/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import liquibase.util.StringUtil;

public class SystemUtil {
    public static boolean isWindows() {
        return StringUtil.trimToEmpty(System.getProperties().getProperty("os.name")).toLowerCase().startsWith("windows");
    }

    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    public static int getJavaMajorVersion() {
        String version = SystemUtil.getJavaVersion();
        String[] splitVersion = version.split("\\.", 2);
        int majorVersion = Integer.parseInt(splitVersion[0]);
        if (majorVersion == 1) {
            splitVersion = splitVersion[1].split("\\.", 2);
            return Integer.parseInt(splitVersion[0]);
        }
        return majorVersion;
    }

    public static boolean isAtLeastJava11() {
        return SystemUtil.getJavaMajorVersion() >= 11;
    }
}

