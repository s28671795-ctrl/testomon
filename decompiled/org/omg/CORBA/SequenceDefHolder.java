/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.SequenceDef;
import org.omg.CORBA.SequenceDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class SequenceDefHolder
implements Streamable {
    public SequenceDef value;

    public SequenceDefHolder() {
    }

    public SequenceDefHolder(SequenceDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SequenceDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SequenceDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        SequenceDefHelper.write(_out, this.value);
    }
}

