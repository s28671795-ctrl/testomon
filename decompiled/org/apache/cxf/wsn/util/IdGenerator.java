/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.util;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;

public class IdGenerator {
    private static final Logger LOGGER = LogUtils.getL7dLogger(IdGenerator.class);
    private static final String UNIQUE_STUB;
    private static int instanceCount;
    private static String hostName;
    private String seed;
    private long sequence;

    public IdGenerator() {
        this("ID:");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IdGenerator(String prefix) {
        String string = UNIQUE_STUB;
        synchronized (string) {
            this.seed = prefix + UNIQUE_STUB + instanceCount++ + ":";
        }
    }

    public static String getHostName() {
        return hostName;
    }

    public synchronized String generateId() {
        return this.seed + this.sequence++;
    }

    public String generateSanitizedId() {
        String result = this.generateId();
        result = result.replace(':', '-');
        result = result.replace('_', '-');
        result = result.replace('.', '-');
        return result;
    }

    static {
        String stub = "";
        boolean canAccessSystemProps = true;
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                sm.checkPropertiesAccess();
            }
        }
        catch (SecurityException se) {
            canAccessSystemProps = false;
        }
        if (canAccessSystemProps) {
            try {
                hostName = InetAddress.getLocalHost().getHostAddress();
                ServerSocket ss = new ServerSocket(0);
                stub = hostName + "-" + Long.toHexString((long)ss.getLocalPort() ^ System.currentTimeMillis()) + "-";
                Thread.sleep(100L);
                ss.close();
            }
            catch (Exception ioe) {
                LOGGER.log(Level.WARNING, "Could not generate unique stub", ioe);
            }
        } else {
            hostName = "localhost";
            stub = hostName + Long.toHexString(System.currentTimeMillis()) + "-";
        }
        UNIQUE_STUB = stub;
    }
}

