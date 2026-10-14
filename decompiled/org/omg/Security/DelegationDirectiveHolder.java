/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.DelegationDirective;
import org.omg.Security.DelegationDirectiveHelper;

public final class DelegationDirectiveHolder
implements Streamable {
    public DelegationDirective value;

    public DelegationDirectiveHolder() {
    }

    public DelegationDirectiveHolder(DelegationDirective initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DelegationDirectiveHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DelegationDirectiveHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        DelegationDirectiveHelper.write(out, this.value);
    }
}

