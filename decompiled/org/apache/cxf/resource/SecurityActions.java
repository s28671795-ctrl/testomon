/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.File;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;

final class SecurityActions {
    private SecurityActions() {
    }

    static boolean fileExists(final File file, Permission permission) {
        SecurityManager sm = System.getSecurityManager();
        if (sm == null) {
            return file.exists();
        }
        sm.checkPermission(permission);
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                return file.exists();
            }
        });
    }
}

