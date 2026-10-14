/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.RTCORBA.PriorityModel;
import org.omg.RTCORBA.PriorityModelHelper;

public final class PriorityModelHolder
implements Streamable {
    public PriorityModel value;

    public PriorityModelHolder() {
    }

    public PriorityModelHolder(PriorityModel initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PriorityModelHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PriorityModelHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        PriorityModelHelper.write(out, this.value);
    }
}

