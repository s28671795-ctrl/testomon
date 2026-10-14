/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.LocateReplyHeader_1_0;
import org.omg.GIOP.LocateReplyHeader_1_0Helper;

public final class LocateReplyHeader_1_0Holder
implements Streamable {
    public LocateReplyHeader_1_0 value;

    public LocateReplyHeader_1_0Holder() {
    }

    public LocateReplyHeader_1_0Holder(LocateReplyHeader_1_0 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LocateReplyHeader_1_0Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = LocateReplyHeader_1_0Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        LocateReplyHeader_1_0Helper.write(_out, this.value);
    }
}

