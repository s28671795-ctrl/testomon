/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.LocalInterfaceDef;
import org.omg.CORBA.LocalInterfaceDefSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class LocalInterfaceDefSeqHolder
implements Streamable {
    public LocalInterfaceDef[] value;

    public LocalInterfaceDefSeqHolder() {
    }

    public LocalInterfaceDefSeqHolder(LocalInterfaceDef[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LocalInterfaceDefSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = LocalInterfaceDefSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        LocalInterfaceDefSeqHelper.write(out, this.value);
    }
}

