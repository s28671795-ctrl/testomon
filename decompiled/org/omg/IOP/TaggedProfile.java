/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.portable.IDLEntity;

public final class TaggedProfile
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int tag;
    public byte[] profile_data;

    public TaggedProfile() {
    }

    public TaggedProfile(int tag, byte[] profile_data) {
        this.tag = tag;
        this.profile_data = profile_data;
    }
}

