/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.IdUniquenessPolicy;
import org.omg.PortableServer.IdUniquenessPolicyHelper;

public final class IdUniquenessPolicyHolder
implements Streamable {
    public IdUniquenessPolicy value;

    public IdUniquenessPolicyHolder() {
    }

    public IdUniquenessPolicyHolder(IdUniquenessPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IdUniquenessPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IdUniquenessPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        IdUniquenessPolicyHelper.write(_out, this.value);
    }
}

