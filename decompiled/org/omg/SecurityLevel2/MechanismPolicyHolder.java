/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.MechanismPolicy;
import org.omg.SecurityLevel2.MechanismPolicyHelper;

public final class MechanismPolicyHolder
implements Streamable {
    public MechanismPolicy value;

    public MechanismPolicyHolder() {
    }

    public MechanismPolicyHolder(MechanismPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return MechanismPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = MechanismPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        MechanismPolicyHelper.write(_out, this.value);
    }
}

