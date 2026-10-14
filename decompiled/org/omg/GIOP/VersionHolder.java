/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.Version;
import org.omg.GIOP.VersionHelper;

public final class VersionHolder
implements Streamable {
    public Version value;

    public VersionHolder() {
    }

    public VersionHolder(Version initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return VersionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = VersionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        VersionHelper.write(_out, this.value);
    }
}

