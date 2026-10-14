/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.impl.ior.iiop.IIOPAddressBase;
import org.jboss.com.sun.corba.se.impl.logging.IORSystemException;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA_2_3.portable.InputStream;

public final class IIOPAddressImpl
extends IIOPAddressBase {
    private IORSystemException wrapper;
    private String host;
    private int port;

    public IIOPAddressImpl(ORB orb, String host2, int port) {
        this.wrapper = IORSystemException.get(orb, "oa.ior");
        if (port < 0 || port > 65535) {
            throw this.wrapper.badIiopAddressPort(new Integer(port));
        }
        this.host = host2;
        this.port = port;
    }

    public IIOPAddressImpl(InputStream is) {
        this.host = is.read_string();
        short thePort = is.read_short();
        this.port = this.shortToInt(thePort);
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }
}

