/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import java.util.Iterator;
import java.util.List;
import org.jboss.com.sun.corba.se.spi.ior.IORFactory;
import org.jboss.com.sun.corba.se.spi.ior.MakeImmutable;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;

public interface IORTemplate
extends List<Object>,
IORFactory,
MakeImmutable {
    public Iterator<Object> iteratorById(int var1);

    public ObjectKeyTemplate getObjectKeyTemplate();
}

