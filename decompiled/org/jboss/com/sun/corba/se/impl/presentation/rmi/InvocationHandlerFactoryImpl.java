/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.DynamicStubImpl;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubInvocationHandlerImpl;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.CompositeInvocationHandlerImpl;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.DelegateInvocationHandlerImpl;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.InvocationHandlerFactory;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.LinkedInvocationHandler;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.DynamicStub;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.omg.CORBA.Object;

public class InvocationHandlerFactoryImpl
implements InvocationHandlerFactory {
    private final PresentationManager.ClassData classData;
    private final PresentationManager pm;
    private Class<?>[] proxyInterfaces;

    public InvocationHandlerFactoryImpl(PresentationManager pm, PresentationManager.ClassData classData) {
        this.classData = classData;
        this.pm = pm;
        Class<?>[] remoteInterfaces = classData.getIDLNameTranslator().getInterfaces();
        this.proxyInterfaces = new Class[remoteInterfaces.length + 1];
        for (int ctr = 0; ctr < remoteInterfaces.length; ++ctr) {
            this.proxyInterfaces[ctr] = remoteInterfaces[ctr];
        }
        this.proxyInterfaces[remoteInterfaces.length] = DynamicStub.class;
    }

    @Override
    public InvocationHandler getInvocationHandler() {
        DynamicStubImpl stub = new DynamicStubImpl(this.classData.getTypeIds());
        return this.getInvocationHandler(stub);
    }

    InvocationHandler getInvocationHandler(DynamicStub stub) {
        InvocationHandler dynamicStubHandler = DelegateInvocationHandlerImpl.create(stub);
        StubInvocationHandlerImpl stubMethodHandler = new StubInvocationHandlerImpl(this.pm, this.classData, stub);
        CustomCompositeInvocationHandlerImpl handler = new CustomCompositeInvocationHandlerImpl(stub);
        handler.addInvocationHandler(DynamicStub.class, dynamicStubHandler);
        handler.addInvocationHandler(Object.class, dynamicStubHandler);
        handler.addInvocationHandler(java.lang.Object.class, dynamicStubHandler);
        handler.setDefaultHandler(stubMethodHandler);
        return handler;
    }

    @Override
    public Class<?>[] getProxyInterfaces() {
        return this.proxyInterfaces;
    }

    private class CustomCompositeInvocationHandlerImpl
    extends CompositeInvocationHandlerImpl
    implements LinkedInvocationHandler,
    Serializable {
        private transient DynamicStub stub;

        @Override
        public void setProxy(Proxy proxy) {
            ((DynamicStubImpl)this.stub).setSelf((DynamicStub)((java.lang.Object)proxy));
        }

        @Override
        public Proxy getProxy() {
            return (Proxy)((java.lang.Object)((DynamicStubImpl)this.stub).getSelf());
        }

        public CustomCompositeInvocationHandlerImpl(DynamicStub stub) {
            this.stub = stub;
        }

        public java.lang.Object writeReplace() throws ObjectStreamException {
            return this.stub;
        }
    }
}

