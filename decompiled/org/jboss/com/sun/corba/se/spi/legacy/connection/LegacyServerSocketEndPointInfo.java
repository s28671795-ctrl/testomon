/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.legacy.connection;

public interface LegacyServerSocketEndPointInfo {
    public static final String DEFAULT_ENDPOINT = "DEFAULT_ENDPOINT";
    public static final String BOOT_NAMING = "BOOT_NAMING";
    public static final String NO_NAME = "NO_NAME";

    public String getType();

    public String getHostName();

    public int getPort();

    public int getLocatorPort();

    public void setLocatorPort(int var1);

    public String getName();
}

