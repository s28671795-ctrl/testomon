/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.IdAssignmentPolicy;
import org.omg.PortableServer.IdAssignmentPolicyHelper;

public final class IdAssignmentPolicyHolder
implements Streamable {
    public IdAssignmentPolicy value;

    public IdAssignmentPolicyHolder() {
    }

    public IdAssignmentPolicyHolder(IdAssignmentPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IdAssignmentPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IdAssignmentPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        IdAssignmentPolicyHelper.write(_out, this.value);
    }
}

