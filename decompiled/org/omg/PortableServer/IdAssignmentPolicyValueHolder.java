/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.IdAssignmentPolicyValueHelper;

public final class IdAssignmentPolicyValueHolder
implements Streamable {
    public IdAssignmentPolicyValue value;

    public IdAssignmentPolicyValueHolder() {
    }

    public IdAssignmentPolicyValueHolder(IdAssignmentPolicyValue initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IdAssignmentPolicyValueHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IdAssignmentPolicyValueHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        IdAssignmentPolicyValueHelper.write(out, this.value);
    }
}

