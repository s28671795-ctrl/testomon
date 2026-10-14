/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtInterfaceDef;
import org.omg.CORBA.ExtInterfaceDefSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtInterfaceDefSeqHolder
implements Streamable {
    public ExtInterfaceDef[] value;

    public ExtInterfaceDefSeqHolder() {
    }

    public ExtInterfaceDefSeqHolder(ExtInterfaceDef[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtInterfaceDefSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtInterfaceDefSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExtInterfaceDefSeqHelper.write(out, this.value);
    }
}

