/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.omg.CORBA.Object;

public abstract class StubFactoryBase
implements PresentationManager.StubFactory {
    private String[] typeIds = null;
    protected final PresentationManager.ClassData classData;

    protected StubFactoryBase(PresentationManager.ClassData classData) {
        this.classData = classData;
    }

    @Override
    public synchronized String[] getTypeIds() {
        if (this.typeIds == null) {
            if (this.classData == null) {
                Object stub = this.makeStub();
                this.typeIds = StubAdapter.getTypeIds(stub);
            } else {
                this.typeIds = this.classData.getTypeIds();
            }
        }
        return this.typeIds;
    }
}

