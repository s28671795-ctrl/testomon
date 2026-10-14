/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class InterfaceDefSeqHolder
implements Streamable {
    public InterfaceDef[] value;

    public InterfaceDefSeqHolder() {
    }

    public InterfaceDefSeqHolder(InterfaceDef[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InterfaceDefSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = InterfaceDefSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        InterfaceDefSeqHelper.write(out, this.value);
    }
}

