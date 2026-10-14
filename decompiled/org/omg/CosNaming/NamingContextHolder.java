/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;

public final class NamingContextHolder
implements Streamable {
    public NamingContext value;

    public NamingContextHolder() {
    }

    public NamingContextHolder(NamingContext initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NamingContextHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = NamingContextHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        NamingContextHelper.write(_out, this.value);
    }
}

