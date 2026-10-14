/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.protocol;

import org.jboss.com.sun.corba.se.pept.protocol.ServerRequestDispatcher;
import org.jboss.com.sun.corba.se.spi.ior.IOR;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;

public interface CorbaServerRequestDispatcher
extends ServerRequestDispatcher {
    public IOR locate(ObjectKey var1);
}

