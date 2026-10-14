/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryFactoryDynamicBase;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryProxyImpl;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;

public class StubFactoryFactoryProxyImpl
extends StubFactoryFactoryDynamicBase {
    @Override
    public PresentationManager.StubFactory makeDynamicStubFactory(PresentationManager pm, PresentationManager.ClassData classData, ClassLoader classLoader) {
        return new StubFactoryProxyImpl(classData, classLoader);
    }
}

