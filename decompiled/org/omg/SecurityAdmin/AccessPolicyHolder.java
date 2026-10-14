/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityAdmin.AccessPolicy;
import org.omg.SecurityAdmin.AccessPolicyHelper;

public final class AccessPolicyHolder
implements Streamable {
    public AccessPolicy value;

    public AccessPolicyHolder() {
    }

    public AccessPolicyHolder(AccessPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AccessPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AccessPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AccessPolicyHelper.write(_out, this.value);
    }
}

