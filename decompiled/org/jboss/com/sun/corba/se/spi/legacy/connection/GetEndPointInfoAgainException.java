/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.legacy.connection;

import org.jboss.com.sun.corba.se.spi.transport.SocketInfo;

public class GetEndPointInfoAgainException
extends Exception {
    private static final long serialVersionUID = -7561363089777958598L;
    private SocketInfo socketInfo;

    public GetEndPointInfoAgainException(SocketInfo socketInfo) {
        this.socketInfo = socketInfo;
    }

    public SocketInfo getEndPointInfo() {
        return this.socketInfo;
    }
}

