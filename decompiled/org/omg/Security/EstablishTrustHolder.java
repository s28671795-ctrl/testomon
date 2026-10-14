/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.EstablishTrust;
import org.omg.Security.EstablishTrustHelper;

public final class EstablishTrustHolder
implements Streamable {
    public EstablishTrust value;

    public EstablishTrustHolder() {
    }

    public EstablishTrustHolder(EstablishTrust initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return EstablishTrustHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = EstablishTrustHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        EstablishTrustHelper.write(_out, this.value);
    }
}

