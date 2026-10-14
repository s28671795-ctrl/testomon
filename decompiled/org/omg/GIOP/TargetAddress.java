/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.GIOP.IORAddressingInfo;
import org.omg.IOP.TaggedProfile;

public final class TargetAddress
implements IDLEntity {
    private short discriminator;
    private byte[] object_key;
    private TaggedProfile profile;
    private IORAddressingInfo ior;

    public short discriminator() {
        return this.discriminator;
    }

    public byte[] object_key() {
        if (this.discriminator != 0) {
            throw new BAD_OPERATION();
        }
        return this.object_key;
    }

    public void object_key(byte[] _x) {
        this.discriminator = 0;
        this.object_key = _x;
    }

    public TaggedProfile profile() {
        if (this.discriminator != 1) {
            throw new BAD_OPERATION();
        }
        return this.profile;
    }

    public void profile(TaggedProfile _x) {
        this.discriminator = 1;
        this.profile = _x;
    }

    public IORAddressingInfo ior() {
        if (this.discriminator != 2) {
            throw new BAD_OPERATION();
        }
        return this.ior;
    }

    public void ior(IORAddressingInfo _x) {
        this.discriminator = (short)2;
        this.ior = _x;
    }

    public void __default() {
        this.discriminator = (short)3;
    }

    public void __default(short _discriminator) {
        if (_discriminator == 0 || _discriminator == 1 || _discriminator == 2) {
            throw new BAD_PARAM("Illegal value is used in __default method", 34, CompletionStatus.COMPLETED_NO);
        }
        this.discriminator = _discriminator;
    }
}

