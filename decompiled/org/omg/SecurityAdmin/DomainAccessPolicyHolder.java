/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityAdmin.DomainAccessPolicy;
import org.omg.SecurityAdmin.DomainAccessPolicyHelper;

public final class DomainAccessPolicyHolder
implements Streamable {
    public DomainAccessPolicy value;

    public DomainAccessPolicyHolder() {
    }

    public DomainAccessPolicyHolder(DomainAccessPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DomainAccessPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DomainAccessPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        DomainAccessPolicyHelper.write(_out, this.value);
    }
}

