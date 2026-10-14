/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.OperationMode;
import org.omg.CORBA.OperationModeHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class OperationModeHolder
implements Streamable {
    public OperationMode value;

    public OperationModeHolder() {
    }

    public OperationModeHolder(OperationMode initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OperationModeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = OperationModeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        OperationModeHelper.write(out, this.value);
    }
}

