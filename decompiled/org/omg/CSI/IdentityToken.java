/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.portable.IDLEntity;

public final class IdentityToken
implements IDLEntity {
    private int discriminator;
    private boolean absent;
    private boolean anonymous;
    private byte[] principal_name;
    private byte[] certificate_chain;
    private byte[] dn;
    private byte[] id;

    public int discriminator() {
        return this.discriminator;
    }

    public boolean absent() {
        if (this.discriminator != 0) {
            throw new BAD_OPERATION();
        }
        return this.absent;
    }

    public void absent(boolean _x) {
        this.discriminator = 0;
        this.absent = _x;
    }

    public boolean anonymous() {
        if (this.discriminator != 1) {
            throw new BAD_OPERATION();
        }
        return this.anonymous;
    }

    public void anonymous(boolean _x) {
        this.discriminator = 1;
        this.anonymous = _x;
    }

    public byte[] principal_name() {
        if (this.discriminator != 2) {
            throw new BAD_OPERATION();
        }
        return this.principal_name;
    }

    public void principal_name(byte[] _x) {
        this.discriminator = 2;
        this.principal_name = _x;
    }

    public byte[] certificate_chain() {
        if (this.discriminator != 4) {
            throw new BAD_OPERATION();
        }
        return this.certificate_chain;
    }

    public void certificate_chain(byte[] _x) {
        this.discriminator = 4;
        this.certificate_chain = _x;
    }

    public byte[] dn() {
        if (this.discriminator != 8) {
            throw new BAD_OPERATION();
        }
        return this.dn;
    }

    public void dn(byte[] _x) {
        this.discriminator = 8;
        this.dn = _x;
    }

    public byte[] id() {
        if (this.discriminator != 3) {
            throw new BAD_OPERATION();
        }
        return this.id;
    }

    public void id(byte[] _x) {
        this.discriminator = 3;
        this.id = _x;
    }

    public void id(int _discriminator, byte[] _x) {
        if (_discriminator != 3) {
            throw new BAD_OPERATION();
        }
        this.discriminator = _discriminator;
        this.id = _x;
    }
}

