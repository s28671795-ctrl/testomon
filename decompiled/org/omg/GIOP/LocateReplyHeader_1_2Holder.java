/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.LocateReplyHeader_1_2;
import org.omg.GIOP.LocateReplyHeader_1_2Helper;

public final class LocateReplyHeader_1_2Holder
implements Streamable {
    public LocateReplyHeader_1_2 value;

    public LocateReplyHeader_1_2Holder() {
    }

    public LocateReplyHeader_1_2Holder(LocateReplyHeader_1_2 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LocateReplyHeader_1_2Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = LocateReplyHeader_1_2Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        LocateReplyHeader_1_2Helper.write(_out, this.value);
    }
}

