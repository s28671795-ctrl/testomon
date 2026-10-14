/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

public class ORBClassLoader {
    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        return ORBClassLoader.getClassLoader().loadClass(className);
    }

    public static ClassLoader getClassLoader() {
        return ORBClassLoader.class.getClassLoader();
    }
}

