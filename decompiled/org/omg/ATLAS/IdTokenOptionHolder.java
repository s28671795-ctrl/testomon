/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.IdTokenOptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.IdentityToken;

public final class IdTokenOptionHolder
implements Streamable {
    public IdentityToken[] value;

    public IdTokenOptionHolder() {
    }

    public IdTokenOptionHolder(IdentityToken[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return IdTokenOptionHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = IdTokenOptionHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        IdTokenOptionHelper.write(out, this.value);
    }
}

