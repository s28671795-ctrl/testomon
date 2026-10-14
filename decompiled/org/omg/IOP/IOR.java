/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.IOP.TaggedProfile;

public final class IOR
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public String type_id = "";
    public TaggedProfile[] profiles;

    public IOR() {
    }

    public IOR(String type_id, TaggedProfile[] profiles) {
        this.type_id = type_id;
        this.profiles = profiles;
    }
}

