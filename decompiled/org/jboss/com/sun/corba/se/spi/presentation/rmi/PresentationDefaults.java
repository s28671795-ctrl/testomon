/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.presentation.rmi;

import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryFactoryProxyImpl;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryFactoryStaticImpl;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryStaticImpl;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;

public abstract class PresentationDefaults {
    private static StubFactoryFactoryStaticImpl staticImpl = null;

    private PresentationDefaults() {
    }

    public static synchronized PresentationManager.StubFactoryFactory getStaticStubFactoryFactory() {
        if (staticImpl == null) {
            staticImpl = new StubFactoryFactoryStaticImpl();
        }
        return staticImpl;
    }

    public static PresentationManager.StubFactoryFactory getProxyStubFactoryFactory() {
        return new StubFactoryFactoryProxyImpl();
    }

    public static PresentationManager.StubFactory makeStaticStubFactory(Class<?> stubClass) {
        return new StubFactoryStaticImpl(stubClass);
    }
}

