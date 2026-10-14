/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SSLIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class SSL
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short target_supports;
    public short target_requires;
    public short port;

    public SSL() {
    }

    public SSL(short target_supports, short target_requires, short port) {
        this.target_supports = target_supports;
        this.target_requires = target_requires;
        this.port = port;
    }
}

