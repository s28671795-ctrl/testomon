/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.TLS_SEC_TRANS;
import org.omg.CSIIOP.TLS_SEC_TRANSHelper;

public final class TLS_SEC_TRANSHolder
implements Streamable {
    public TLS_SEC_TRANS value;

    public TLS_SEC_TRANSHolder() {
    }

    public TLS_SEC_TRANSHolder(TLS_SEC_TRANS initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TLS_SEC_TRANSHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = TLS_SEC_TRANSHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        TLS_SEC_TRANSHelper.write(_out, this.value);
    }
}

