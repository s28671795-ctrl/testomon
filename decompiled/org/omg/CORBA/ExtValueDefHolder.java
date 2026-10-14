/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtValueDef;
import org.omg.CORBA.ExtValueDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtValueDefHolder
implements Streamable {
    public ExtValueDef value;

    public ExtValueDefHolder() {
    }

    public ExtValueDefHolder(ExtValueDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtValueDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtValueDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ExtValueDefHelper.write(_out, this.value);
    }
}

