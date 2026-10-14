/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.TransportAddress;
import org.omg.CSIIOP.TransportAddressListHelper;

public final class TransportAddressListHolder
implements Streamable {
    public TransportAddress[] value;

    public TransportAddressListHolder() {
    }

    public TransportAddressListHolder(TransportAddress[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TransportAddressListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = TransportAddressListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        TransportAddressListHelper.write(out, this.value);
    }
}

