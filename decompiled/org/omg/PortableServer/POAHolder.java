/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public final class POAHolder
implements Streamable {
    public POA value;

    public POAHolder() {
    }

    public POAHolder(POA initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return POAHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = POAHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        POAHelper.write(_out, this.value);
    }
}

