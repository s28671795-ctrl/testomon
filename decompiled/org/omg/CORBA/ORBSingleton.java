/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.ORB;

public class ORBSingleton {
    protected static final String DEFAULT_ORB_KEY = "org.omg.CORBA.ORBClass";
    protected static final String DEFAULT_ORB_VALUE = "org.jacorb.orb.ORB";
    protected static final String DEFAULT_ORB_SINGLETON_VALUE = "org.jacorb.orb.ORBSingleton";
    protected static final Object SYNCHRONIZER = new Object();
    protected static final boolean useTCCL;
    protected static ORB singleton;

    protected static ORB create(String className) {
        ClassLoader cl = useTCCL ? (Thread.currentThread().getContextClassLoader() != null ? Thread.currentThread().getContextClassLoader() : ClassLoader.getSystemClassLoader()) : ORB.class.getClassLoader();
        try {
            return (ORB)Class.forName(className, true, cl).newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new INITIALIZE("Could not instantiate ORB implementation: " + className);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ORB init() {
        Object object = SYNCHRONIZER;
        synchronized (object) {
            if (singleton == null) {
                singleton = ORBSingleton.create(DEFAULT_ORB_SINGLETON_VALUE);
            }
        }
        return singleton;
    }

    static {
        singleton = null;
        String clpolicy = System.getProperty("jacorb.classloaderpolicy", "tccl");
        useTCCL = !clpolicy.equalsIgnoreCase("forname");
    }
}

