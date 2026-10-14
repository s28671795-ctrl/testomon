/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import org.jboss.com.sun.corba.se.impl.orbutil.RepIdDelegator;
import org.jboss.com.sun.corba.se.impl.orbutil.RepIdDelegator_1_3;
import org.jboss.com.sun.corba.se.impl.orbutil.RepIdDelegator_1_3_1;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdStrings;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdUtility;
import org.jboss.com.sun.corba.se.spi.orb.ORB;

public abstract class RepositoryIdFactory {
    private static final RepIdDelegator_1_3 legacyDelegator = new RepIdDelegator_1_3();
    private static final RepIdDelegator_1_3_1 ladybirdDelegator = new RepIdDelegator_1_3_1();
    private static final RepIdDelegator currentDelegator = new RepIdDelegator();

    public static RepositoryIdStrings getRepIdStringsFactory() {
        return currentDelegator;
    }

    public static RepositoryIdStrings getRepIdStringsFactory(ORB orb) {
        if (orb != null) {
            switch (orb.getORBVersion().getORBType()) {
                case 0: 
                case 3: 
                case 10: {
                    return currentDelegator;
                }
                case 1: {
                    return legacyDelegator;
                }
                case 2: {
                    return ladybirdDelegator;
                }
            }
            return currentDelegator;
        }
        return currentDelegator;
    }

    public static RepositoryIdUtility getRepIdUtility() {
        return currentDelegator;
    }

    public static RepositoryIdUtility getRepIdUtility(ORB orb) {
        if (orb != null) {
            switch (orb.getORBVersion().getORBType()) {
                case 0: 
                case 3: 
                case 10: {
                    return currentDelegator;
                }
                case 1: {
                    return legacyDelegator;
                }
                case 2: {
                    return ladybirdDelegator;
                }
            }
            return currentDelegator;
        }
        return currentDelegator;
    }
}

