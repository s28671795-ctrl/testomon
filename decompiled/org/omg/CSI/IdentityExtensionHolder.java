/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.IdentityExtensionHelper;

public final class IdentityExtensionHolder
implements Streamable {
    public byte[] value;

    public IdentityExtensionHolder() {
    }

    public IdentityExtensionHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IdentityExtensionHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IdentityExtensionHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        IdentityExtensionHelper.write(out, this.value);
    }
}

