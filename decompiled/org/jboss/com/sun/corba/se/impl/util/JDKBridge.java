/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.util;

import java.net.MalformedURLException;
import java.rmi.server.RMIClassLoader;
import java.security.AccessController;
import org.jboss.com.sun.corba.se.impl.orbutil.GetPropertyAction;
import org.jboss.com.sun.corba.se.impl.util.JDKClassLoader;

public class JDKBridge {
    private static final String LOCAL_CODEBASE_KEY = "java.rmi.server.codebase";
    private static final String USE_CODEBASE_ONLY_KEY = "java.rmi.server.useCodebaseOnly";
    private static String localCodebase = null;
    private static boolean useCodebaseOnly;

    public static String getLocalCodebase() {
        return localCodebase;
    }

    public static boolean useCodebaseOnly() {
        return useCodebaseOnly;
    }

    public static Class<?> loadClass(String className, String remoteCodebase, ClassLoader loader) throws ClassNotFoundException {
        if (loader == null) {
            return JDKBridge.loadClassM(className, remoteCodebase, useCodebaseOnly);
        }
        try {
            return JDKBridge.loadClassM(className, remoteCodebase, useCodebaseOnly);
        }
        catch (ClassNotFoundException e) {
            return loader.loadClass(className);
        }
    }

    public static Class<?> loadClass(String className, String remoteCodebase) throws ClassNotFoundException {
        return JDKBridge.loadClass(className, remoteCodebase, null);
    }

    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        return JDKBridge.loadClass(className, null, null);
    }

    public static final void main(String[] args2) {
        System.out.println("1.2 VM");
    }

    public static synchronized void setCodebaseProperties() {
        String prop = AccessController.doPrivileged(new GetPropertyAction(LOCAL_CODEBASE_KEY));
        if (prop != null && prop.trim().length() > 0) {
            localCodebase = prop;
        }
        if ((prop = AccessController.doPrivileged(new GetPropertyAction(USE_CODEBASE_ONLY_KEY))) != null && prop.trim().length() > 0) {
            useCodebaseOnly = Boolean.valueOf(prop);
        }
    }

    public static synchronized void setLocalCodebase(String codebase) {
        localCodebase = codebase;
    }

    private static Class<?> loadClassM(String className, String remoteCodebase, boolean useCodebaseOnly) throws ClassNotFoundException {
        try {
            return JDKClassLoader.loadClass(null, className);
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                if (!useCodebaseOnly && remoteCodebase != null) {
                    return RMIClassLoader.loadClass(remoteCodebase, className);
                }
                return RMIClassLoader.loadClass((String)null, className);
            }
            catch (MalformedURLException e) {
                className = className + ": " + e.toString();
                throw new ClassNotFoundException(className);
            }
        }
    }

    static {
        JDKBridge.setCodebaseProperties();
    }
}

