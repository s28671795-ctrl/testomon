/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.portable.IDLEntity;

public final class FragmentHeader_1_2
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public int request_id;

    public FragmentHeader_1_2() {
    }

    public FragmentHeader_1_2(int request_id) {
        this.request_id = request_id;
    }
}

