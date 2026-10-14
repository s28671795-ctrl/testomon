/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AbstractInterfaceDef;
import org.omg.CORBA.AbstractInterfaceDefSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AbstractInterfaceDefSeqHolder
implements Streamable {
    public AbstractInterfaceDef[] value;

    public AbstractInterfaceDefSeqHolder() {
    }

    public AbstractInterfaceDefSeqHolder(AbstractInterfaceDef[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AbstractInterfaceDefSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AbstractInterfaceDefSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AbstractInterfaceDefSeqHelper.write(out, this.value);
    }
}

