/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.IdUniquenessPolicyValue;
import org.omg.PortableServer.IdUniquenessPolicyValueHelper;

public final class IdUniquenessPolicyValueHolder
implements Streamable {
    public IdUniquenessPolicyValue value;

    public IdUniquenessPolicyValueHolder() {
    }

    public IdUniquenessPolicyValueHolder(IdUniquenessPolicyValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IdUniquenessPolicyValueHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IdUniquenessPolicyValueHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        IdUniquenessPolicyValueHelper.write(out, this.value);
    }
}

