/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.RequestHeader_1_1;
import org.omg.GIOP.RequestHeader_1_1Helper;

public final class RequestHeader_1_1Holder
implements Streamable {
    public RequestHeader_1_1 value;

    public RequestHeader_1_1Holder() {
    }

    public RequestHeader_1_1Holder(RequestHeader_1_1 initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RequestHeader_1_1Helper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = RequestHeader_1_1Helper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        RequestHeader_1_1Helper.write(_out, this.value);
    }
}

