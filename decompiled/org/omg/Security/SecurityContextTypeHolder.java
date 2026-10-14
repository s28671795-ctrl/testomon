/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.SecurityContextType;
import org.omg.Security.SecurityContextTypeHelper;

public final class SecurityContextTypeHolder
implements Streamable {
    public SecurityContextType value;

    public SecurityContextTypeHolder() {
    }

    public SecurityContextTypeHolder(SecurityContextType initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SecurityContextTypeHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SecurityContextTypeHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        SecurityContextTypeHelper.write(out, this.value);
    }
}

