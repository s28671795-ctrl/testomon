/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.EstablishTrustPolicy;
import org.omg.SecurityLevel2.EstablishTrustPolicyHelper;

public final class EstablishTrustPolicyHolder
implements Streamable {
    public EstablishTrustPolicy value;

    public EstablishTrustPolicyHolder() {
    }

    public EstablishTrustPolicyHolder(EstablishTrustPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return EstablishTrustPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = EstablishTrustPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        EstablishTrustPolicyHelper.write(_out, this.value);
    }
}

