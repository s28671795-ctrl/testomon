/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAPackage.InvalidPolicy;
import org.omg.PortableServer.POAPackage.InvalidPolicyHelper;

public final class InvalidPolicyHolder
implements Streamable {
    public InvalidPolicy value;

    public InvalidPolicyHolder() {
    }

    public InvalidPolicyHolder(InvalidPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return InvalidPolicyHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = InvalidPolicyHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        InvalidPolicyHelper.write(_out, this.value);
    }
}

