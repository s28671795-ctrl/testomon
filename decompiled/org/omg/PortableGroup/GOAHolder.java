/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.GOA;
import org.omg.PortableGroup.GOAHelper;

public final class GOAHolder
implements Streamable {
    public GOA value;

    public GOAHolder() {
    }

    public GOAHolder(GOA initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return GOAHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = GOAHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        GOAHelper.write(_out, this.value);
    }
}

