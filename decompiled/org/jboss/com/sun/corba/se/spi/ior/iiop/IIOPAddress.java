/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior.iiop;

import org.jboss.com.sun.corba.se.spi.ior.Writeable;

public interface IIOPAddress
extends Writeable {
    public String getHost();

    public int getPort();
}

