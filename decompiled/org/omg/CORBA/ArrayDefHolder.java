/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ArrayDef;
import org.omg.CORBA.ArrayDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ArrayDefHolder
implements Streamable {
    public ArrayDef value;

    public ArrayDefHolder() {
    }

    public ArrayDefHolder(ArrayDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ArrayDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ArrayDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ArrayDefHelper.write(_out, this.value);
    }
}

