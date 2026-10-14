/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.legacy.connection;

import org.jboss.com.sun.corba.se.spi.legacy.connection.LegacyServerSocketEndPointInfo;

public interface LegacyServerSocketManager {
    public int legacyGetTransientServerPort(String var1);

    public int legacyGetPersistentServerPort(String var1);

    public int legacyGetTransientOrPersistentServerPort(String var1);

    public LegacyServerSocketEndPointInfo legacyGetEndpoint(String var1);

    public boolean legacyIsLocalServerPort(int var1);
}

