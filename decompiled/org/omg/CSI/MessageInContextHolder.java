/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.MessageInContext;
import org.omg.CSI.MessageInContextHelper;

public final class MessageInContextHolder
implements Streamable {
    public MessageInContext value;

    public MessageInContextHolder() {
    }

    public MessageInContextHolder(MessageInContext initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MessageInContextHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = MessageInContextHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        MessageInContextHelper.write(_out, this.value);
    }
}

