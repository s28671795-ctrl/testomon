/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class Version
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public byte major;
    public byte minor;

    public Version() {
    }

    public Version(byte major, byte minor) {
        this.major = major;
        this.minor = minor;
    }
}

