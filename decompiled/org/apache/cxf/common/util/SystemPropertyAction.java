/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;

public final class SystemPropertyAction
implements PrivilegedAction<String> {
    private static final Logger LOG = LogUtils.getL7dLogger(SystemPropertyAction.class);
    private final String property;
    private final String def;

    private SystemPropertyAction(String name) {
        this.property = name;
        this.def = null;
    }

    private SystemPropertyAction(String name, String d) {
        this.property = name;
        this.def = d;
    }

    @Override
    public String run() {
        if (this.def != null) {
            return System.getProperty(this.property, this.def);
        }
        return System.getProperty(this.property);
    }

    public static String getProperty(String name) {
        return AccessController.doPrivileged(new SystemPropertyAction(name));
    }

    public static String getProperty(String name, String def) {
        try {
            return AccessController.doPrivileged(new SystemPropertyAction(name, def));
        }
        catch (SecurityException ex) {
            LOG.log(Level.FINE, "SecurityException raised getting property " + name, ex);
            return def;
        }
    }

    public static String getPropertyOrNull(String name) {
        try {
            return AccessController.doPrivileged(new SystemPropertyAction(name));
        }
        catch (SecurityException ex) {
            LOG.log(Level.FINE, "SecurityException raised getting property " + name, ex);
            return null;
        }
    }

    public static int getInteger(final String name, final int def) {
        try {
            return AccessController.doPrivileged(new PrivilegedAction<Integer>(){

                @Override
                public Integer run() {
                    return Integer.getInteger(name, def);
                }
            });
        }
        catch (SecurityException ex) {
            LOG.log(Level.FINE, "SecurityException raised getting property " + name, ex);
            return def;
        }
    }
}

