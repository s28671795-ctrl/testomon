/*
 * Decompiled with CFR 0.152.
 */
package org.omg.TimeBase;

import org.omg.CORBA.portable.IDLEntity;

public final class IntervalT
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public long lower_bound;
    public long upper_bound;

    public IntervalT() {
    }

    public IntervalT(long lower_bound, long upper_bound) {
        this.lower_bound = lower_bound;
        this.upper_bound = upper_bound;
    }
}

