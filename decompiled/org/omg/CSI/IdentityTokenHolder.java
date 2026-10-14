/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.IdentityToken;
import org.omg.CSI.IdentityTokenHelper;

public final class IdentityTokenHolder
implements Streamable {
    public IdentityToken value;

    public IdentityTokenHolder() {
    }

    public IdentityTokenHolder(IdentityToken initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IdentityTokenHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IdentityTokenHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        IdentityTokenHelper.write(out, this.value);
    }
}

