/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.portable.IDLEntity;

public final class PriorityBand
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short low;
    public short high;

    public PriorityBand() {
    }

    public PriorityBand(short low, short high) {
        this.low = low;
        this.high = high;
    }
}

