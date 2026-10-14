/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

enum OperatingSystem {
    MAC,
    LINUX,
    ANDROID,
    WINDOWS,
    UNKNOWN;


    String getResolvedOsName() {
        return System.getProperty("os.name").toLowerCase();
    }

    static OperatingSystem get() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        if (operatingSystem.contains("windows")) {
            return WINDOWS;
        }
        if (operatingSystem.contains("mac")) {
            return MAC;
        }
        if (operatingSystem.contains("linux")) {
            String javaVendor = System.getProperty("java.vendor", "").toLowerCase();
            String javaVmVendor = System.getProperty("java.vm.vendor", "").toLowerCase();
            String javaRuntimeName = System.getProperty("java.runtime.name", "").toLowerCase();
            if (javaVendor.equals("the android project") || javaVmVendor.equals("the android project") || javaRuntimeName.equals("android runtime")) {
                return ANDROID;
            }
            return LINUX;
        }
        return UNKNOWN;
    }
}

