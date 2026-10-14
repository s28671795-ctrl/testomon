/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.PollableSetPackage;

import org.omg.CORBA.PollableSetPackage.NoPossiblePollable;
import org.omg.CORBA.PollableSetPackage.NoPossiblePollableHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class NoPossiblePollableHolder
implements Streamable {
    public NoPossiblePollable value;

    public NoPossiblePollableHolder() {
    }

    public NoPossiblePollableHolder(NoPossiblePollable initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NoPossiblePollableHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NoPossiblePollableHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NoPossiblePollableHelper.write(_out, this.value);
    }
}

