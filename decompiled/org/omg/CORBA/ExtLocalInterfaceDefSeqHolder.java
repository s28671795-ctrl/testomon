/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtLocalInterfaceDef;
import org.omg.CORBA.ExtLocalInterfaceDefSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtLocalInterfaceDefSeqHolder
implements Streamable {
    public ExtLocalInterfaceDef[] value;

    public ExtLocalInterfaceDefSeqHolder() {
    }

    public ExtLocalInterfaceDefSeqHolder(ExtLocalInterfaceDef[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtLocalInterfaceDefSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtLocalInterfaceDefSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExtLocalInterfaceDefSeqHelper.write(out, this.value);
    }
}

