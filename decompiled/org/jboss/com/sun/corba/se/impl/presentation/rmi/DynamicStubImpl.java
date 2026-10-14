/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import org.jboss.com.sun.corba.se.impl.ior.StubIORImpl;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.InvocationHandlerFactoryImpl;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubConnectImpl;
import org.jboss.com.sun.corba.se.impl.util.JDKBridge;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.DynamicStub;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA_2_3.portable.ObjectImpl;

public class DynamicStubImpl
extends ObjectImpl
implements DynamicStub,
Serializable {
    private static final long serialVersionUID = 4852612040012087675L;
    private String[] typeIds;
    private StubIORImpl ior;
    private DynamicStub self = null;

    public void setSelf(DynamicStub self) {
        this.self = self;
    }

    public DynamicStub getSelf() {
        return this.self;
    }

    public DynamicStubImpl(String[] typeIds) {
        this.typeIds = typeIds;
        this.ior = null;
    }

    @Override
    public void setDelegate(Delegate delegate) {
        this._set_delegate(delegate);
    }

    @Override
    public Delegate getDelegate() {
        return this._get_delegate();
    }

    @Override
    public ORB getORB() {
        return this._orb();
    }

    @Override
    public String[] _ids() {
        return this.typeIds;
    }

    @Override
    public String[] getTypeIds() {
        return this._ids();
    }

    @Override
    public void connect(ORB orb) throws RemoteException {
        this.ior = StubConnectImpl.connect(this.ior, this.self, this, orb);
    }

    @Override
    public boolean isLocal() {
        return this._is_local();
    }

    @Override
    public OutputStream request(String operation, boolean responseExpected) {
        return this._request(operation, responseExpected);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        this.ior = new StubIORImpl();
        this.ior.doRead(stream);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        if (this.ior == null) {
            this.ior = new StubIORImpl(this);
        }
        this.ior.doWrite(stream);
    }

    public Object readResolve() {
        String repositoryId = this.ior.getRepositoryId();
        String cname = RepositoryId.cache.getId(repositoryId).getClassName();
        Class<?> cls = null;
        try {
            cls = JDKBridge.loadClass(cname, null, null);
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        PresentationManager pm = org.jboss.com.sun.corba.se.spi.orb.ORB.getPresentationManager();
        PresentationManager.ClassData classData = pm.getClassData(cls);
        InvocationHandlerFactoryImpl ihfactory = (InvocationHandlerFactoryImpl)classData.getInvocationHandlerFactory();
        return ihfactory.getInvocationHandler(this);
    }
}

