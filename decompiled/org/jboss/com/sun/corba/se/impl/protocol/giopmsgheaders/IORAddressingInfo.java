/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.IOP.IOR;

public final class IORAddressingInfo
implements IDLEntity {
    public int selected_profile_index = 0;
    public IOR ior = null;

    public IORAddressingInfo() {
    }

    public IORAddressingInfo(int _selected_profile_index, IOR _ior) {
        this.selected_profile_index = _selected_profile_index;
        this.ior = _ior;
    }
}

