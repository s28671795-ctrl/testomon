/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.ReplyHandler;
import org.omg.Messaging.ReplyHandlerHelper;

public final class ReplyHandlerHolder
implements Streamable {
    public ReplyHandler value;

    public ReplyHandlerHolder() {
    }

    public ReplyHandlerHolder(ReplyHandler initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ReplyHandlerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ReplyHandlerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ReplyHandlerHelper.write(_out, this.value);
    }
}

