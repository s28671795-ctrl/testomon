/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior.iiop;

import org.jboss.com.sun.corba.se.spi.ior.TaggedComponent;
import org.jboss.com.sun.corba.se.spi.ior.iiop.IIOPAddress;

public interface AlternateIIOPAddressComponent
extends TaggedComponent {
    public IIOPAddress getAddress();
}

