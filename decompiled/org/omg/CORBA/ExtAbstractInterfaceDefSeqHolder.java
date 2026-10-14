/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ExtAbstractInterfaceDef;
import org.omg.CORBA.ExtAbstractInterfaceDefSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ExtAbstractInterfaceDefSeqHolder
implements Streamable {
    public ExtAbstractInterfaceDef[] value;

    public ExtAbstractInterfaceDefSeqHolder() {
    }

    public ExtAbstractInterfaceDefSeqHolder(ExtAbstractInterfaceDef[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ExtAbstractInterfaceDefSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ExtAbstractInterfaceDefSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        ExtAbstractInterfaceDefSeqHelper.write(out, this.value);
    }
}

