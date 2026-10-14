/*
 * Decompiled with CFR 0.152.
 */
package org.omg.TimeBase;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.TimeBase.IntervalT;
import org.omg.TimeBase.IntervalTHelper;

public final class IntervalTHolder
implements Streamable {
    public IntervalT value;

    public IntervalTHolder() {
    }

    public IntervalTHolder(IntervalT initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IntervalTHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = IntervalTHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        IntervalTHelper.write(_out, this.value);
    }
}

