/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryBase;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.omg.CORBA.Object;

public abstract class StubFactoryDynamicBase
extends StubFactoryBase {
    protected final ClassLoader loader;

    public StubFactoryDynamicBase(PresentationManager.ClassData classData, ClassLoader loader) {
        super(classData);
        if (loader == null) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl == null) {
                cl = ClassLoader.getSystemClassLoader();
            }
            this.loader = cl;
        } else {
            this.loader = loader;
        }
    }

    @Override
    public abstract Object makeStub();
}

