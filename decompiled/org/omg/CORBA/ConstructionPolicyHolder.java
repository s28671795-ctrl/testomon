/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ConstructionPolicy;
import org.omg.CORBA.ConstructionPolicyHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ConstructionPolicyHolder
implements Streamable {
    public ConstructionPolicy value;

    public ConstructionPolicyHolder() {
    }

    public ConstructionPolicyHolder(ConstructionPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ConstructionPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ConstructionPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ConstructionPolicyHelper.write(_out, this.value);
    }
}

