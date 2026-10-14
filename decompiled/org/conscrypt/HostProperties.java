/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.File;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

class HostProperties {
    private static final Logger logger = Logger.getLogger(HostProperties.class.getName());
    private static final String TEMP_DIR_PROPERTY_NAME = "org.conscrypt.tmpdir";
    static final OperatingSystem OS = HostProperties.getOperatingSystem(System.getProperty("os.name", ""));
    static final Architecture ARCH = HostProperties.getArchitecture(System.getProperty("os.arch", ""));

    static boolean isWindows() {
        return OS == OperatingSystem.WINDOWS;
    }

    static boolean isOSX() {
        return OS == OperatingSystem.OSX;
    }

    static File getTempDir() {
        File f;
        try {
            f = HostProperties.toDirectory(System.getProperty(TEMP_DIR_PROPERTY_NAME));
            if (f != null) {
                return f;
            }
            f = HostProperties.toDirectory(System.getProperty("java.io.tmpdir"));
            if (f != null) {
                return f;
            }
            if (HostProperties.isWindows()) {
                f = HostProperties.toDirectory(System.getenv("TEMP"));
                if (f != null) {
                    return f;
                }
                String userprofile = System.getenv("USERPROFILE");
                if (userprofile != null) {
                    f = HostProperties.toDirectory(userprofile + "\\AppData\\Local\\Temp");
                    if (f != null) {
                        return f;
                    }
                    f = HostProperties.toDirectory(userprofile + "\\Local Settings\\Temp");
                    if (f != null) {
                        return f;
                    }
                }
            } else {
                f = HostProperties.toDirectory(System.getenv("TMPDIR"));
                if (f != null) {
                    return f;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        f = HostProperties.isWindows() ? new File("C:\\Windows\\Temp") : new File("/tmp");
        logger.log(Level.WARNING, "Failed to get the temporary directory; falling back to: {0}", f);
        return f;
    }

    private static File toDirectory(String path) {
        if (path == null) {
            return null;
        }
        File f = new File(path);
        f.mkdirs();
        if (!f.isDirectory()) {
            return null;
        }
        try {
            return f.getAbsoluteFile();
        }
        catch (Exception ignored) {
            return f;
        }
    }

    private static String normalize(String value) {
        return value.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }

    private static OperatingSystem getOperatingSystem(String value) {
        if ((value = HostProperties.normalize(value)).startsWith("aix")) {
            return OperatingSystem.AIX;
        }
        if (value.startsWith("hpux")) {
            return OperatingSystem.HPUX;
        }
        if (value.startsWith("os400") && (value.length() <= 5 || !Character.isDigit(value.charAt(5)))) {
            return OperatingSystem.OS400;
        }
        if (value.startsWith("linux")) {
            return OperatingSystem.LINUX;
        }
        if (value.startsWith("macosx") || value.startsWith("osx")) {
            return OperatingSystem.OSX;
        }
        if (value.startsWith("freebsd")) {
            return OperatingSystem.FREEBSD;
        }
        if (value.startsWith("openbsd")) {
            return OperatingSystem.OPENBSD;
        }
        if (value.startsWith("netbsd")) {
            return OperatingSystem.NETBSD;
        }
        if (value.startsWith("solaris") || value.startsWith("sunos")) {
            return OperatingSystem.SUNOS;
        }
        if (value.startsWith("windows")) {
            return OperatingSystem.WINDOWS;
        }
        return OperatingSystem.UNKNOWN;
    }

    private static Architecture getArchitecture(String value) {
        if ((value = HostProperties.normalize(value)).matches("^(x8664|amd64|ia32e|em64t|x64)$")) {
            return Architecture.X86_64;
        }
        if (value.matches("^(x8632|x86|i[3-6]86|ia32|x32)$")) {
            return Architecture.X86_32;
        }
        if (value.matches("^(ia64|itanium64)$")) {
            return Architecture.ITANIUM_64;
        }
        if (value.matches("^(sparc|sparc32)$")) {
            return Architecture.SPARC_32;
        }
        if (value.matches("^(sparcv9|sparc64)$")) {
            return Architecture.SPARC_64;
        }
        if (value.matches("^(arm|arm32)$")) {
            return Architecture.ARM_32;
        }
        if ("aarch64".equals(value)) {
            return Architecture.AARCH_64;
        }
        if (value.matches("^(ppc|ppc32)$")) {
            return Architecture.PPC_32;
        }
        if ("ppc64".equals(value)) {
            return Architecture.PPC_64;
        }
        if ("ppc64le".equals(value)) {
            return Architecture.PPCLE_64;
        }
        if ("s390".equals(value)) {
            return Architecture.S390_32;
        }
        if ("s390x".equals(value)) {
            return Architecture.S390_64;
        }
        return Architecture.UNKNOWN;
    }

    private HostProperties() {
    }

    static enum Architecture {
        X86_64,
        X86_32{

            @Override
            public String getFileComponent() {
                return "x86";
            }
        }
        ,
        ITANIUM_64,
        SPARC_32,
        SPARC_64,
        ARM_32,
        AARCH_64,
        PPC_32,
        PPC_64,
        PPCLE_64,
        S390_32,
        S390_64,
        UNKNOWN;


        public String getFileComponent() {
            return this.name().toLowerCase();
        }
    }

    static enum OperatingSystem {
        AIX,
        HPUX,
        OS400,
        LINUX,
        OSX,
        FREEBSD,
        OPENBSD,
        NETBSD,
        SUNOS,
        WINDOWS,
        UNKNOWN;


        public String getFileComponent() {
            return this.name().toLowerCase();
        }
    }
}

