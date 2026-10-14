/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class IDLTypeHolder
implements Streamable {
    public IDLType value;

    public IDLTypeHolder() {
    }

    public IDLTypeHolder(IDLType initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IDLTypeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IDLTypeHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        IDLTypeHelper.write(_out, this.value);
    }
}

