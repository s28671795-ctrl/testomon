/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.AS_ContextSec;
import org.omg.CSIIOP.AS_ContextSecHelper;

public final class AS_ContextSecHolder
implements Streamable {
    public AS_ContextSec value;

    public AS_ContextSecHolder() {
    }

    public AS_ContextSecHolder(AS_ContextSec initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AS_ContextSecHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = AS_ContextSecHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        AS_ContextSecHelper.write(_out, this.value);
    }
}

