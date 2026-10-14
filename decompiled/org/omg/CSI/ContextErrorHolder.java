/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.ContextError;
import org.omg.CSI.ContextErrorHelper;

public final class ContextErrorHolder
implements Streamable {
    public ContextError value;

    public ContextErrorHolder() {
    }

    public ContextErrorHolder(ContextError initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ContextErrorHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ContextErrorHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ContextErrorHelper.write(_out, this.value);
    }
}

