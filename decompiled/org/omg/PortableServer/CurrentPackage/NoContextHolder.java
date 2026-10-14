/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.CurrentPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.CurrentPackage.NoContext;
import org.omg.PortableServer.CurrentPackage.NoContextHelper;

public final class NoContextHolder
implements Streamable {
    public NoContext value;

    public NoContextHolder() {
    }

    public NoContextHolder(NoContext initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NoContextHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = NoContextHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        NoContextHelper.write(_out, this.value);
    }
}

