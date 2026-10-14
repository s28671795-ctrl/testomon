/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.SecurityContextState;
import org.omg.Security.SecurityContextStateHelper;

public final class SecurityContextStateHolder
implements Streamable {
    public SecurityContextState value;

    public SecurityContextStateHolder() {
    }

    public SecurityContextStateHolder(SecurityContextState initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SecurityContextStateHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SecurityContextStateHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        SecurityContextStateHelper.write(out, this.value);
    }
}

