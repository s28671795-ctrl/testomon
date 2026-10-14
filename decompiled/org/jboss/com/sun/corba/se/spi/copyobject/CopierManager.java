/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.copyobject;

import org.jboss.com.sun.corba.se.spi.copyobject.ObjectCopierFactory;

public interface CopierManager {
    public void setDefaultId(int var1);

    public int getDefaultId();

    public ObjectCopierFactory getObjectCopierFactory(int var1);

    public ObjectCopierFactory getDefaultObjectCopierFactory();

    public void registerObjectCopierFactory(ObjectCopierFactory var1, int var2);
}

