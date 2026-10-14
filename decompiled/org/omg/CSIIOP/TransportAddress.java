/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class TransportAddress
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String host_name = "";
    public short port;

    public TransportAddress() {
    }

    public TransportAddress(String host_name, short port) {
        this.host_name = host_name;
        this.port = port;
    }
}

