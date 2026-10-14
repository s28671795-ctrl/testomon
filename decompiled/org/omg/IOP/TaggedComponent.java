/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IOP;

import org.omg.CORBA.portable.IDLEntity;

public final class TaggedComponent
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int tag;
    public byte[] component_data;

    public TaggedComponent() {
    }

    public TaggedComponent(int tag, byte[] component_data) {
        this.tag = tag;
        this.component_data = component_data;
    }
}

