/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.SAS_ContextSec;
import org.omg.CSIIOP.SAS_ContextSecHelper;

public final class SAS_ContextSecHolder
implements Streamable {
    public SAS_ContextSec value;

    public SAS_ContextSecHolder() {
    }

    public SAS_ContextSecHolder(SAS_ContextSec initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SAS_ContextSecHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = SAS_ContextSecHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        SAS_ContextSecHelper.write(_out, this.value);
    }
}

