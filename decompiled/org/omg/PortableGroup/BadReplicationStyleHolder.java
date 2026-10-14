/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableGroup.BadReplicationStyle;
import org.omg.PortableGroup.BadReplicationStyleHelper;

public final class BadReplicationStyleHolder
implements Streamable {
    public BadReplicationStyle value;

    public BadReplicationStyleHolder() {
    }

    public BadReplicationStyleHolder(BadReplicationStyle initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BadReplicationStyleHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = BadReplicationStyleHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        BadReplicationStyleHelper.write(_out, this.value);
    }
}

