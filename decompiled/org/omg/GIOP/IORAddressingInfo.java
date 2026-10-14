/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.IOP.IOR;

public final class IORAddressingInfo
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int selected_profile_index;
    public IOR ior;

    public IORAddressingInfo() {
    }

    public IORAddressingInfo(int selected_profile_index, IOR ior) {
        this.selected_profile_index = selected_profile_index;
        this.ior = ior;
    }
}

