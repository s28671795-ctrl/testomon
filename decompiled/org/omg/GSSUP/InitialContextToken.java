/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GSSUP;

import org.omg.CORBA.portable.IDLEntity;

public final class InitialContextToken
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public byte[] username;
    public byte[] password;
    public byte[] target_name;

    public InitialContextToken() {
    }

    public InitialContextToken(byte[] username, byte[] password, byte[] target_name) {
        this.username = username;
        this.password = password;
        this.target_name = target_name;
    }
}

