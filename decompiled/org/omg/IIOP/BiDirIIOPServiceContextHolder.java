/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.IIOP.BiDirIIOPServiceContext;
import org.omg.IIOP.BiDirIIOPServiceContextHelper;

public final class BiDirIIOPServiceContextHolder
implements Streamable {
    public BiDirIIOPServiceContext value;

    public BiDirIIOPServiceContextHolder() {
    }

    public BiDirIIOPServiceContextHolder(BiDirIIOPServiceContext initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BiDirIIOPServiceContextHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = BiDirIIOPServiceContextHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        BiDirIIOPServiceContextHelper.write(_out, this.value);
    }
}

