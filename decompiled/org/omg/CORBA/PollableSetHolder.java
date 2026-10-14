/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.PollableSet;
import org.omg.CORBA.PollableSetHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PollableSetHolder
implements Streamable {
    public PollableSet value;

    public PollableSetHolder() {
    }

    public PollableSetHolder(PollableSet initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PollableSetHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PollableSetHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        PollableSetHelper.write(_out, this.value);
    }
}

