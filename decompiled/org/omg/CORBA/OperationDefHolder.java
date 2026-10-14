/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.OperationDef;
import org.omg.CORBA.OperationDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class OperationDefHolder
implements Streamable {
    public OperationDef value;

    public OperationDefHolder() {
    }

    public OperationDefHolder(OperationDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OperationDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = OperationDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        OperationDefHelper.write(_out, this.value);
    }
}

