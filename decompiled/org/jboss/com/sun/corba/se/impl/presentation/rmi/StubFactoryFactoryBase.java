/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;

public abstract class StubFactoryFactoryBase
implements PresentationManager.StubFactoryFactory {
    @Override
    public String getStubName(String fullName) {
        return Utility.stubName(fullName);
    }
}

