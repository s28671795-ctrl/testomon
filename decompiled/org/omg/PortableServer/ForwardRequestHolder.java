/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.ForwardRequestHelper;

public final class ForwardRequestHolder
implements Streamable {
    public ForwardRequest value;

    public ForwardRequestHolder() {
    }

    public ForwardRequestHolder(ForwardRequest initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ForwardRequestHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ForwardRequestHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ForwardRequestHelper.write(_out, this.value);
    }
}

