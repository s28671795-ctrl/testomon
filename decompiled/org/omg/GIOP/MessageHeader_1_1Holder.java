/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.MessageHeader_1_1;
import org.omg.GIOP.MessageHeader_1_1Helper;

public final class MessageHeader_1_1Holder
implements Streamable {
    public MessageHeader_1_1 value;

    public MessageHeader_1_1Holder() {
    }

    public MessageHeader_1_1Holder(MessageHeader_1_1 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MessageHeader_1_1Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = MessageHeader_1_1Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        MessageHeader_1_1Helper.write(_out, this.value);
    }
}

