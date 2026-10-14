/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class ListenPoint
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String host = "";
    public short port;

    public ListenPoint() {
    }

    public ListenPoint(String host2, short port) {
        this.host = host2;
        this.port = port;
    }
}

