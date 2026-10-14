/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.protocol;

import java.util.Set;
import org.jboss.com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapterFactory;
import org.jboss.com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
import org.jboss.com.sun.corba.se.spi.protocol.LocalClientRequestDispatcherFactory;

public interface RequestDispatcherRegistry {
    public void registerClientRequestDispatcher(ClientRequestDispatcher var1, int var2);

    public ClientRequestDispatcher getClientRequestDispatcher(int var1);

    public void registerLocalClientRequestDispatcherFactory(LocalClientRequestDispatcherFactory var1, int var2);

    public LocalClientRequestDispatcherFactory getLocalClientRequestDispatcherFactory(int var1);

    public void registerServerRequestDispatcher(CorbaServerRequestDispatcher var1, int var2);

    public CorbaServerRequestDispatcher getServerRequestDispatcher(int var1);

    public void registerServerRequestDispatcher(CorbaServerRequestDispatcher var1, String var2);

    public CorbaServerRequestDispatcher getServerRequestDispatcher(String var1);

    public void registerObjectAdapterFactory(ObjectAdapterFactory var1, int var2);

    public ObjectAdapterFactory getObjectAdapterFactory(int var1);

    public Set<Object> getObjectAdapterFactories();
}

