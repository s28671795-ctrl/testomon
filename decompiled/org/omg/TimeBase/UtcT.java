/*
 * Decompiled with CFR 0.152.
 */
package org.omg.TimeBase;

import org.omg.CORBA.portable.IDLEntity;

public final class UtcT
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public long time;
    public int inacclo;
    public short inacchi;
    public short tdf;

    public UtcT() {
    }

    public UtcT(long time, int inacclo, short inacchi, short tdf) {
        this.time = time;
        this.inacclo = inacclo;
        this.inacchi = inacchi;
        this.tdf = tdf;
    }
}

