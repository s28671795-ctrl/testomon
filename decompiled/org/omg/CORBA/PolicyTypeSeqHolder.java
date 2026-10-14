/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.PolicyTypeSeqHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class PolicyTypeSeqHolder
implements Streamable {
    public int[] value;

    public PolicyTypeSeqHolder() {
    }

    public PolicyTypeSeqHolder(int[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return PolicyTypeSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = PolicyTypeSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        PolicyTypeSeqHelper.write(out, this.value);
    }
}

