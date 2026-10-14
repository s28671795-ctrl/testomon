/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.IRObject;
import org.omg.CORBA.IRObjectHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class IRObjectHolder
implements Streamable {
    public IRObject value;

    public IRObjectHolder() {
    }

    public IRObjectHolder(IRObject initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IRObjectHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IRObjectHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        IRObjectHelper.write(_out, this.value);
    }
}

