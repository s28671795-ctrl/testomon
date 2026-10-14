/*
 * Decompiled with CFR 0.152.
 */
package org.omg.BiDirPolicy;

import org.omg.BiDirPolicy.BidirectionalPolicy;
import org.omg.BiDirPolicy.BidirectionalPolicyHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class BidirectionalPolicyHolder
implements Streamable {
    public BidirectionalPolicy value;

    public BidirectionalPolicyHolder() {
    }

    public BidirectionalPolicyHolder(BidirectionalPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return BidirectionalPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = BidirectionalPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        BidirectionalPolicyHelper.write(_out, this.value);
    }
}

