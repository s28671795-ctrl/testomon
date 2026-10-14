/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.LocateRequestHeader_1_2;
import org.omg.GIOP.LocateRequestHeader_1_2Helper;

public final class LocateRequestHeader_1_2Holder
implements Streamable {
    public LocateRequestHeader_1_2 value;

    public LocateRequestHeader_1_2Holder() {
    }

    public LocateRequestHeader_1_2Holder(LocateRequestHeader_1_2 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return LocateRequestHeader_1_2Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = LocateRequestHeader_1_2Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        LocateRequestHeader_1_2Helper.write(_out, this.value);
    }
}

