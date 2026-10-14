/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.PriorityBand;
import org.omg.RTCORBA.PriorityBandsHelper;

public final class PriorityBandsHolder
implements Streamable {
    public PriorityBand[] value;

    public PriorityBandsHolder() {
    }

    public PriorityBandsHolder(PriorityBand[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PriorityBandsHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PriorityBandsHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        PriorityBandsHelper.write(out, this.value);
    }
}

