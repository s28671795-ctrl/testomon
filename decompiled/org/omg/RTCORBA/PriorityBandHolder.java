/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.PriorityBand;
import org.omg.RTCORBA.PriorityBandHelper;

public final class PriorityBandHolder
implements Streamable {
    public PriorityBand value;

    public PriorityBandHolder() {
    }

    public PriorityBandHolder(PriorityBand initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PriorityBandHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = PriorityBandHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        PriorityBandHelper.write(_out, this.value);
    }
}

