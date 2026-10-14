/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.IORAddressingInfo;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.IOP.TaggedProfile;

public final class TargetAddress
implements IDLEntity {
    private byte[] ___object_key;
    private TaggedProfile ___profile;
    private IORAddressingInfo ___ior;
    private short __discriminator;
    private boolean __uninitialized = true;

    public short discriminator() {
        if (this.__uninitialized) {
            throw new BAD_OPERATION();
        }
        return this.__discriminator;
    }

    public byte[] object_key() {
        if (this.__uninitialized) {
            throw new BAD_OPERATION();
        }
        this.verifyobject_key(this.__discriminator);
        return this.___object_key;
    }

    public void object_key(byte[] value) {
        this.__discriminator = 0;
        this.___object_key = value;
        this.__uninitialized = false;
    }

    private void verifyobject_key(short discriminator) {
        if (discriminator != 0) {
            throw new BAD_OPERATION();
        }
    }

    public TaggedProfile profile() {
        if (this.__uninitialized) {
            throw new BAD_OPERATION();
        }
        this.verifyprofile(this.__discriminator);
        return this.___profile;
    }

    public void profile(TaggedProfile value) {
        this.__discriminator = 1;
        this.___profile = value;
        this.__uninitialized = false;
    }

    private void verifyprofile(short discriminator) {
        if (discriminator != 1) {
            throw new BAD_OPERATION();
        }
    }

    public IORAddressingInfo ior() {
        if (this.__uninitialized) {
            throw new BAD_OPERATION();
        }
        this.verifyior(this.__discriminator);
        return this.___ior;
    }

    public void ior(IORAddressingInfo value) {
        this.__discriminator = (short)2;
        this.___ior = value;
        this.__uninitialized = false;
    }

    private void verifyior(short discriminator) {
        if (discriminator != 2) {
            throw new BAD_OPERATION();
        }
    }

    public void _default() {
        this.__discriminator = Short.MIN_VALUE;
        this.__uninitialized = false;
    }
}

