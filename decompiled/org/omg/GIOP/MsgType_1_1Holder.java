/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.MsgType_1_1;
import org.omg.GIOP.MsgType_1_1Helper;

public final class MsgType_1_1Holder
implements Streamable {
    public MsgType_1_1 value;

    public MsgType_1_1Holder() {
    }

    public MsgType_1_1Holder(MsgType_1_1 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MsgType_1_1Helper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = MsgType_1_1Helper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        MsgType_1_1Helper.write(out, this.value);
    }
}

