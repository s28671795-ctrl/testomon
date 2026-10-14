/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.lang.reflect.Proxy;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryDynamicBase;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.InvocationHandlerFactory;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.LinkedInvocationHandler;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.DynamicStub;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.omg.CORBA.Object;

public class StubFactoryProxyImpl
extends StubFactoryDynamicBase {
    public StubFactoryProxyImpl(PresentationManager.ClassData classData, ClassLoader loader) {
        super(classData, loader);
    }

    @Override
    public Object makeStub() {
        InvocationHandlerFactory factory2 = this.classData.getInvocationHandlerFactory();
        LinkedInvocationHandler handler = (LinkedInvocationHandler)factory2.getInvocationHandler();
        Class<?>[] interfaces = factory2.getProxyInterfaces();
        DynamicStub stub = (DynamicStub)Proxy.newProxyInstance(this.loader, interfaces, handler);
        handler.setProxy((Proxy)((java.lang.Object)stub));
        return stub;
    }
}

