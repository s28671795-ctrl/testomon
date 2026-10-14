/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CSIIOP.CompoundSecMech;

public final class CompoundSecMechList
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public boolean stateful;
    public CompoundSecMech[] mechanism_list;

    public CompoundSecMechList() {
    }

    public CompoundSecMechList(boolean stateful, CompoundSecMech[] mechanism_list) {
        this.stateful = stateful;
        this.mechanism_list = mechanism_list;
    }
}

