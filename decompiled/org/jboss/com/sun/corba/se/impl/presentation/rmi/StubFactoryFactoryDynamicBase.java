/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.rmi.Remote;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.Util;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.ReflectiveTie;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryFactoryBase;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.IDLEntity;

public abstract class StubFactoryFactoryDynamicBase
extends StubFactoryFactoryBase {
    protected final ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.presentation");

    @Override
    public PresentationManager.StubFactory createStubFactory(String className, boolean isIDLStub, String remoteCodeBase, Class<?> expectedClass, ClassLoader classLoader) {
        Class<?> cls = null;
        try {
            cls = Util.loadClass(className, remoteCodeBase, classLoader);
        }
        catch (ClassNotFoundException exc) {
            throw this.wrapper.classNotFound3(CompletionStatus.COMPLETED_MAYBE, exc, className);
        }
        PresentationManager pm = ORB.getPresentationManager();
        if (IDLEntity.class.isAssignableFrom(cls) && !Remote.class.isAssignableFrom(cls)) {
            PresentationManager.StubFactoryFactory sff = pm.getStubFactoryFactory(false);
            PresentationManager.StubFactory sf = sff.createStubFactory(className, true, remoteCodeBase, expectedClass, classLoader);
            return sf;
        }
        PresentationManager.ClassData classData = pm.getClassData(cls);
        return this.makeDynamicStubFactory(pm, classData, classLoader);
    }

    public abstract PresentationManager.StubFactory makeDynamicStubFactory(PresentationManager var1, PresentationManager.ClassData var2, ClassLoader var3);

    @Override
    public Tie getTie(Class<?> cls) {
        PresentationManager pm = ORB.getPresentationManager();
        return new ReflectiveTie(pm, this.wrapper);
    }

    @Override
    public boolean createsDynamicStubs() {
        return true;
    }
}

