/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.OpaqueBuffer;
import org.omg.Security.OpaqueBufferHelper;

public final class OpaqueBufferHolder
implements Streamable {
    public OpaqueBuffer value;

    public OpaqueBufferHolder() {
    }

    public OpaqueBufferHolder(OpaqueBuffer initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return OpaqueBufferHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = OpaqueBufferHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        OpaqueBufferHelper.write(_out, this.value);
    }
}

