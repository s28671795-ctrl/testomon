/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.TransportAddress;
import org.omg.CSIIOP.TransportAddressHelper;

public final class TransportAddressHolder
implements Streamable {
    public TransportAddress value;

    public TransportAddressHolder() {
    }

    public TransportAddressHolder(TransportAddress initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TransportAddressHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = TransportAddressHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        TransportAddressHelper.write(_out, this.value);
    }
}

