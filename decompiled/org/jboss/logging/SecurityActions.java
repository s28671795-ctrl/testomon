/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.logging;

import java.security.AccessController;
import java.security.PrivilegedAction;

class SecurityActions {
    SecurityActions() {
    }

    static String getSystemProperty(final String key) {
        if (System.getSecurityManager() == null) {
            return System.getProperty(key);
        }
        return AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return System.getProperty(key);
            }
        });
    }

    static String getSystemProperty(final String key, final String dft) {
        if (System.getSecurityManager() == null) {
            return System.getProperty(key, dft);
        }
        return AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return System.getProperty(key, dft);
            }
        });
    }
}

