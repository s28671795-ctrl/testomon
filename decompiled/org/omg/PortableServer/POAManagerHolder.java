/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerHelper;

public final class POAManagerHolder
implements Streamable {
    public POAManager value;

    public POAManagerHolder() {
    }

    public POAManagerHolder(POAManager initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return POAManagerHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = POAManagerHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        POAManagerHelper.write(_out, this.value);
    }
}

