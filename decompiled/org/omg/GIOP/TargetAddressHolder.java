/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.GIOP.TargetAddress;
import org.omg.GIOP.TargetAddressHelper;

public final class TargetAddressHolder
implements Streamable {
    public TargetAddress value;

    public TargetAddressHolder() {
    }

    public TargetAddressHolder(TargetAddress initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return TargetAddressHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = TargetAddressHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        TargetAddressHelper.write(out, this.value);
    }
}

