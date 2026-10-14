/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.presentation.rmi;

import java.lang.reflect.Method;
import java.util.Map;
import javax.rmi.CORBA.Tie;
import org.jboss.com.sun.corba.se.spi.orbutil.proxy.InvocationHandlerFactory;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.DynamicMethodMarshaller;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
import org.omg.CORBA.Object;

public interface PresentationManager {
    public ClassData getClassData(Class<?> var1);

    public DynamicMethodMarshaller getDynamicMethodMarshaller(Method var1);

    public StubFactoryFactory getStubFactoryFactory(boolean var1);

    public void setStubFactoryFactory(boolean var1, StubFactoryFactory var2);

    public Tie getTie();

    public boolean useDynamicStubs();

    public static interface ClassData {
        public Class<?> getMyClass();

        public IDLNameTranslator getIDLNameTranslator();

        public String[] getTypeIds();

        public InvocationHandlerFactory getInvocationHandlerFactory();

        public Map<java.lang.Object, java.lang.Object> getDictionary();
    }

    public static interface StubFactory {
        public Object makeStub();

        public String[] getTypeIds();
    }

    public static interface StubFactoryFactory {
        public String getStubName(String var1);

        public StubFactory createStubFactory(String var1, boolean var2, String var3, Class<?> var4, ClassLoader var5);

        public Tie getTie(Class<?> var1);

        public boolean createsDynamicStubs();
    }
}

