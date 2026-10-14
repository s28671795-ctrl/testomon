/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.portable.IDLEntity;

public final class Encoding
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short format;
    public byte major_version;
    public byte minor_version;

    public Encoding() {
    }

    public Encoding(short format, byte major_version, byte minor_version) {
        this.format = format;
        this.major_version = major_version;
        this.minor_version = minor_version;
    }
}

