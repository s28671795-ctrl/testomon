/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypedefDef;
import org.omg.CORBA.TypedefDefHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class TypedefDefHolder
implements Streamable {
    public TypedefDef value;

    public TypedefDefHolder() {
    }

    public TypedefDefHolder(TypedefDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TypedefDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = TypedefDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        TypedefDefHelper.write(_out, this.value);
    }
}

