/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.IIOP.Version;

public final class ProfileBody_1_0
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public Version iiop_version;
    public String host = "";
    public short port;
    public byte[] object_key;

    public ProfileBody_1_0() {
    }

    public ProfileBody_1_0(Version iiop_version, String host2, short port, byte[] object_key) {
        this.iiop_version = iiop_version;
        this.host = host2;
        this.port = port;
        this.object_key = object_key;
    }
}

