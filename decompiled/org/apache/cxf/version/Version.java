/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Version {
    private static String version;
    private static String name;
    private static String fullVersion;
    private static String buildNumber;
    private static final String VERSION_BASE = "/org/apache/cxf/version/";

    private Version() {
    }

    private static InputStream getResourceAsStream(String resource) {
        ClassLoader cl = Version.class.getClassLoader();
        InputStream ins = cl.getResourceAsStream(resource);
        if (ins == null && resource.startsWith("/")) {
            ins = cl.getResourceAsStream(resource.substring(1));
        }
        return ins;
    }

    private static synchronized void loadProperties() {
        if (version == null) {
            Properties p = new Properties();
            try (InputStream ins = Version.getResourceAsStream("/org/apache/cxf/version/version.properties");){
                p.load(ins);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            version = p.getProperty("product.version", "<unknown>");
            name = p.getProperty("product.name", "Apache CXF");
            buildNumber = p.getProperty("build.number", "<unknown>");
            fullVersion = version.contains("SNAPSHOT") || version.contains("<unknown>") ? name + " " + version + "-" + buildNumber : name + " " + version;
        }
    }

    public static String getCurrentVersion() {
        Version.loadProperties();
        return version;
    }

    public static String getName() {
        Version.loadProperties();
        return name;
    }

    public static String getCompleteVersionString() {
        Version.loadProperties();
        return fullVersion;
    }
}

