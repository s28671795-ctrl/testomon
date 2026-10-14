/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.SystemExceptionReplyBody;
import org.omg.GIOP.SystemExceptionReplyBodyHelper;

public final class SystemExceptionReplyBodyHolder
implements Streamable {
    public SystemExceptionReplyBody value;

    public SystemExceptionReplyBodyHolder() {
    }

    public SystemExceptionReplyBodyHolder(SystemExceptionReplyBody initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SystemExceptionReplyBodyHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = SystemExceptionReplyBodyHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        SystemExceptionReplyBodyHelper.write(_out, this.value);
    }
}

