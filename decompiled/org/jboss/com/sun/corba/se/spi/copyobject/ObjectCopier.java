/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.copyobject;

import org.jboss.com.sun.corba.se.spi.copyobject.ReflectiveCopyException;

public interface ObjectCopier {
    public Object copy(Object var1) throws ReflectiveCopyException;
}

