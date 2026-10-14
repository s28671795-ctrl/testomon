/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.PollableSetPackage;

import org.omg.CORBA.PollableSetPackage.UnknownPollable;
import org.omg.CORBA.PollableSetPackage.UnknownPollableHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class UnknownPollableHolder
implements Streamable {
    public UnknownPollable value;

    public UnknownPollableHolder() {
    }

    public UnknownPollableHolder(UnknownPollable initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return UnknownPollableHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = UnknownPollableHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        UnknownPollableHelper.write(_out, this.value);
    }
}

