/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.ReplyHeader_1_0;
import org.omg.GIOP.ReplyHeader_1_0Helper;

public final class ReplyHeader_1_0Holder
implements Streamable {
    public ReplyHeader_1_0 value;

    public ReplyHeader_1_0Holder() {
    }

    public ReplyHeader_1_0Holder(ReplyHeader_1_0 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ReplyHeader_1_0Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ReplyHeader_1_0Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ReplyHeader_1_0Helper.write(_out, this.value);
    }
}

