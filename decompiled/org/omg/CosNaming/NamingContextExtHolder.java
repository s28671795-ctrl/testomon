/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public final class NamingContextExtHolder
implements Streamable {
    public NamingContextExt value;

    public NamingContextExtHolder() {
    }

    public NamingContextExtHolder(NamingContextExt initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NamingContextExtHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = NamingContextExtHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        NamingContextExtHelper.write(_out, this.value);
    }
}

