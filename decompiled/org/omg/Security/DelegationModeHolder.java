/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.DelegationMode;
import org.omg.Security.DelegationModeHelper;

public final class DelegationModeHolder
implements Streamable {
    public DelegationMode value;

    public DelegationModeHolder() {
    }

    public DelegationModeHolder(DelegationMode initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DelegationModeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DelegationModeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        DelegationModeHelper.write(out, this.value);
    }
}

