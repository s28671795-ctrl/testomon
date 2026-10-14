/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryBase;
import org.omg.CORBA.Object;

public class StubFactoryStaticImpl
extends StubFactoryBase {
    private Class<?> stubClass;

    public StubFactoryStaticImpl(Class<?> cls) {
        super(null);
        this.stubClass = cls;
    }

    @Override
    public Object makeStub() {
        Object stub = null;
        try {
            stub = (Object)this.stubClass.newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return stub;
    }
}

