/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import org.codehaus.commons.nullanalysis.NotNullByDefault;

public final class Sandbox {
    private final AccessControlContext accessControlContext;

    public Sandbox(PermissionCollection permissions) {
        this.accessControlContext = new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
    }

    public <R> R confine(PrivilegedAction<R> action) {
        return AccessController.doPrivileged(action, this.accessControlContext);
    }

    public <R> R confine(PrivilegedExceptionAction<R> action) throws Exception {
        try {
            return AccessController.doPrivileged(action, this.accessControlContext);
        }
        catch (PrivilegedActionException pae) {
            throw pae.getException();
        }
    }

    static {
        if (System.getSecurityManager() == null) {
            Policy.setPolicy(new Policy(){

                @Override
                @NotNullByDefault(value=false)
                public PermissionCollection getPermissions(CodeSource codesource) {
                    for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
                        if (!"sun.rmi.server.LoaderHandler".equals(element.getClassName()) || !"loadClass".equals(element.getMethodName())) continue;
                        return new Permissions();
                    }
                    return super.getPermissions(codesource);
                }

                @Override
                @NotNullByDefault(value=false)
                public boolean implies(ProtectionDomain domain, Permission permission) {
                    return true;
                }
            });
            System.setSecurityManager(new SecurityManager());
        }
    }
}

