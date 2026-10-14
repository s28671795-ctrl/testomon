/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeDescription;
import org.omg.CORBA.TypeDescriptionHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class TypeDescriptionHolder
implements Streamable {
    public TypeDescription value;

    public TypeDescriptionHolder() {
    }

    public TypeDescriptionHolder(TypeDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TypeDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = TypeDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        TypeDescriptionHelper.write(_out, this.value);
    }
}

